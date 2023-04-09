package com.fullstack.quizchallengeservice.service;

import com.fullstack.quizchallengeservice.entity.BadgeEntity;
import com.fullstack.quizchallengeservice.entity.PlayerEntity;
import com.fullstack.quizchallengeservice.model.Player;
import com.fullstack.quizchallengeservice.repository.BadgeRepository;
import com.fullstack.quizchallengeservice.repository.PlayerRepository;
import com.fullstack.quizchallengeservice.utils.badges.*;
import com.github.javafaker.Faker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProfileServiceImp implements ProfileService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private BadgeRepository badgeRepository;

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
                    .build();
        }
    }

    public String generateUsername() {
        Faker faker = new Faker();
        return faker.superhero().prefix() + faker.name().firstName() + faker.address().buildingNumber();
    }

    @Override
    public Optional<Player> getProfile(String id) {
        return Optional.empty();
    }
}