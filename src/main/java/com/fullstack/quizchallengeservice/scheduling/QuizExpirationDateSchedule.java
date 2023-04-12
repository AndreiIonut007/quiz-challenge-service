package com.fullstack.quizchallengeservice.scheduling;

import com.fullstack.quizchallengeservice.entity.BadgeEntity;
import com.fullstack.quizchallengeservice.entity.PlayerEntity;
import com.fullstack.quizchallengeservice.entity.QuizEntity;
import com.fullstack.quizchallengeservice.entity.SaveQuizAnswersEntity;
import com.fullstack.quizchallengeservice.repository.BadgeRepository;
import com.fullstack.quizchallengeservice.repository.PlayerRepository;
import com.fullstack.quizchallengeservice.repository.QuizRepository;
import com.fullstack.quizchallengeservice.repository.SaveQuizAnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class QuizExpirationDateSchedule {

    @Autowired
    private SaveQuizAnswersRepository saveQuizAnswersRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    @Scheduled(fixedRate = 20000)
    public void scheduleTask() {
        Set<QuizEntity> quizEntities = quizRepository.findExpiredQuizzes(new Date());
        for (var quizEntity : quizEntities) {
            List<SaveQuizAnswersEntity> answersEntities = getParticipants(quizEntity.getId()).stream().sorted(Comparator.comparing(SaveQuizAnswersEntity::getCorrectAnswers).reversed()).toList();
            for (int i = 0; i < 3; i++) {
                Optional<PlayerEntity> player = playerRepository.findById(answersEntities.get(i).getPlayer());
                int finalI = i;
                player.ifPresent(playerEntity -> playerEntity.setTokens(playerEntity.getTokens() + getReward(finalI, quizEntity, player.get())));
                player.ifPresent(playerEntity -> playerRepository.save(playerEntity));
            }
        }

    }

    public Long getReward(int id, QuizEntity quizEntity, PlayerEntity playerEntity) {
        Long val = 0L;
        switch (id) {
            case 0 -> {
                val = quizEntity.getRewards().getWinner();
                if (val > 0L) {
                    BadgeEntity badgeEntity = badgeRepository.findOffensiveBadgeByPlayer(playerEntity);
                    badgeEntity.setLevel(badgeEntity.getLevel() + 1);
                    badgeRepository.save(badgeEntity);
                }
            }
            case 1 -> {
                val = quizEntity.getRewards().getSecPlace();
                if (val > 0L) {
                    BadgeEntity badgeEntity = badgeRepository.findOffensiveBadgeByPlayer(playerEntity);
                    badgeEntity.setLevel(badgeEntity.getLevel() + 1);
                    badgeRepository.save(badgeEntity);
                }
            }
            case 2 -> {
                val = quizEntity.getRewards().getThirdPlace();
                if (val > 0L) {
                    BadgeEntity badgeEntity = badgeRepository.findOffensiveBadgeByPlayer(playerEntity);
                    badgeEntity.setLevel(badgeEntity.getLevel() + 1);
                    badgeRepository.save(badgeEntity);
                }
            }
        }
        return val;
    }

    private List<SaveQuizAnswersEntity> getParticipants(Long id) {
        return saveQuizAnswersRepository.findAllByQuizId(id);
    }
}
