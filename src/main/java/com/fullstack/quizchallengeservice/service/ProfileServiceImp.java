package com.fullstack.quizchallengeservice.service;

import com.fullstack.quizchallengeservice.entity.BadgeEntity;
import com.fullstack.quizchallengeservice.entity.PlayerEntity;
import com.fullstack.quizchallengeservice.model.Player;
import com.fullstack.quizchallengeservice.model.Quiz;
import com.fullstack.quizchallengeservice.repository.BadgeRepository;
import com.fullstack.quizchallengeservice.repository.PlayerRepository;
import com.fullstack.quizchallengeservice.repository.QuizRepository;
import com.fullstack.quizchallengeservice.utils.badges.*;
import com.github.javafaker.Faker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImp implements ProfileService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public Player configProfile(Player player) {
        Optional<PlayerEntity> optionalPlayer = playerRepository.findById(player.getEmail());
        if (optionalPlayer.isEmpty()) {
            PlayerEntity playerEntity = PlayerEntity.builder()
                    .email(player.getEmail())
                    .username(generateUsername())
                    .tokens(100L)
                    .build();
            playerEntity = playerRepository.save(playerEntity);
            setDefaultBadges(playerEntity);
            BeanUtils.copyProperties(playerEntity, player);
        } else BeanUtils.copyProperties(optionalPlayer.get(), player);
        player.setBadges(getBadges(playerRepository.getReferenceById(player.getEmail())));
        player.setRank(getRank(player.getEmail()));
        return player;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Boolean validateQuizCreation(String id) {
        PlayerEntity playerEntity = playerRepository.getReferenceById(id);
        BadgeEntity badgeEntity = badgeRepository.findDefensiveBadgeByPlayer(playerEntity);
        if (badgeEntity.getLastUpdate().isEqual(LocalDate.now())){
            return badgeEntity.getExecutedToday().compareTo(badgeEntity.getLevel()) < 0 && badgeEntity.isValid();
        }else{
            badgeEntity.setLastUpdate(LocalDate.now());
            badgeEntity.setExecutedToday(0);
            badgeEntity.setValid(true);
            badgeRepository.save(badgeEntity);
            return true;
        }
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Boolean validateQuizSelection(String id) {
        PlayerEntity playerEntity = playerRepository.getReferenceById(id);
        BadgeEntity badgeEntity = badgeRepository.findOffensiveBadgeByPlayer(playerEntity);
        if (badgeEntity.getLastUpdate().isEqual(LocalDate.now())){
            return badgeEntity.getExecutedToday().compareTo(badgeEntity.getLevel()) < 0 && badgeEntity.isValid();
        }else{
            badgeEntity.setLastUpdate(LocalDate.now());
            badgeEntity.setExecutedToday(0);
            badgeEntity.setValid(true);
            badgeRepository.save(badgeEntity);
            return true;
        }
    }

    /**
     * @return
     */
    @Override
    public List<Player> getRanking() {
         List<PlayerEntity> playerEntities = playerRepository.findAll(Sort.by("tokens").descending());
        return playerEntities.stream()
                .map((playerEntity -> Player.builder()
                        .email(playerEntity.getEmail())
                        .username(playerEntity.getUsername())
                        .tokens(playerEntity.getTokens())
                        .build()))
                .collect(Collectors.toList());
    }

    private Set<BadgeEntity> getBadges(PlayerEntity player) {
        return badgeRepository.findAllByPlayer(player);
    }

    private Integer getRank(String email) {
        return playerRepository.getRank(email);
    }

    private void setDefaultBadges(PlayerEntity player) {
        DefaultBadgesType[] defaultBadges = DefaultBadgesType.values();
        for (var badge : defaultBadges) {
            BadgeEntity badgeEntity = BadgeEntity.builder()
                    .type(badge.toString())
                    .player(player)
                    .level(1)
                    .executedToday(0)
                    .lastUpdate(LocalDate.now())
                    .valid(true)
                    .build();
            badgeRepository.save(badgeEntity);
        }
    }

    public String generateUsername() {
        Faker faker = new Faker();
        return faker.superhero().prefix() + faker.name().firstName() + faker.address().buildingNumber();
    }

}