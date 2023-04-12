package com.fullstack.quizchallengeservice.service;

import com.fullstack.quizchallengeservice.entity.*;
import com.fullstack.quizchallengeservice.model.Quiz;
import com.fullstack.quizchallengeservice.repository.BadgeRepository;
import com.fullstack.quizchallengeservice.repository.PlayerRepository;
import com.fullstack.quizchallengeservice.repository.QuizRepository;
import com.fullstack.quizchallengeservice.repository.SaveQuizAnswersRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private SaveQuizAnswersRepository saveQuizAnswersRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    @Override
    public Quiz addQuiz(Quiz quiz) {
        Optional<PlayerEntity> playerEntity = playerRepository.findById(quiz.getCreator());
        if (playerEntity.isPresent()) {
            QuizEntity quizEntity = new QuizEntity();
            BeanUtils.copyProperties(quiz, quizEntity);
            quizEntity = quizRepository.save(quizEntity);
            quiz.setId(quizEntity.getId());

            Long debit = quizEntity.getRewards().getWinner() + quizEntity.getRewards().getSecPlace() + quizEntity.getRewards().getThirdPlace();
            playerEntity.get().setTokens(playerEntity.get().getTokens() - debit);
            playerRepository.save(playerEntity.get());

            BadgeEntity badgeEntity = badgeRepository.findDefensiveBadgeByPlayer(playerEntity.get());
            badgeEntity.setExecutedToday(badgeEntity.getExecutedToday() + 1);
            if (badgeEntity.getExecutedToday().compareTo(badgeEntity.getLevel()) == 0) {
                badgeEntity.setValid(false);
                badgeEntity.setLevel(badgeEntity.getLevel() + 1);
                playerEntity.get().setTokens(playerEntity.get().getTokens() + (10 / getRank(playerEntity.get().getEmail())) * playerEntity.get().getTokens());
                playerRepository.save(playerEntity.get());
            }
            badgeRepository.save(badgeEntity);
        }

        return quiz;
    }

    private Integer getRank(String email) {
        return playerRepository.getRank(email);
    }

    @Override
    public List<Quiz> getAvailableQuizzes(String email) {
        List<SaveQuizAnswersEntity> savedAnswers = saveQuizAnswersRepository.findAllByPlayer(email);
        List<QuizEntity> quizEntities =
                quizRepository.findByCreatorNot(email).stream().filter(quizEntity -> savedAnswers.stream().noneMatch(saveQuizAnswersEntity -> Objects.equals(saveQuizAnswersEntity.getQuizId(), quizEntity.getId()))).toList();
        return quizEntities.stream()
                .map((quizEntity -> Quiz.builder()
                        .id(quizEntity.getId())
                        .title(quizEntity.getTitle())
                        .expDate(quizEntity.getExpDate())
                        .timer(quizEntity.getTimer())
                        .creator(quizEntity.getCreator())
                        .questions(quizEntity.getQuestions())
                        .rewards(quizEntity.getRewards())
                        .build()))
                .collect(Collectors.toList());
    }

    private boolean checkSavedAnswers(Long id, List<SaveQuizAnswersEntity> savedAnswers) {
        return savedAnswers.stream().noneMatch(saveQuizAnswersEntity -> Objects.equals(saveQuizAnswersEntity.getQuizId(), id));
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Quiz getQuiById(String id) {
        Optional<QuizEntity> quizEntity = quizRepository.findById(Long.parseLong(id));
        Quiz quiz = new Quiz();
        quizEntity.ifPresent(entity -> BeanUtils.copyProperties(entity, quiz));
        return quiz;
    }

    /**
     * @param checkboxArray
     * @param quizId
     * @param playerId
     * @return
     */
    @Override
    public Integer saveAnswers(Boolean[][] checkboxArray, String quizId, String playerId) {
        Optional<QuizEntity> quizEntity = quizRepository.findById(Long.parseLong(quizId));
        int correctAnswers = -1;
        if (quizEntity.isPresent()) {
            List<Question> result = quizEntity.get().getQuestions();
            correctAnswers = result.size();
            for (int i = 0; i < result.size(); i++) {
                var ans = result.get(i).getAnswersTypes();
                for (int j = 0; j < ans.size(); j++) {
                    if ((!ans.get(j) && checkboxArray[i][j]) || (ans.get(j) && !checkboxArray[i][j])) {
                        correctAnswers -= 1;
                        break;
                    }
                }
            }
            saveQuizAnswersRepository.save(SaveQuizAnswersEntity.builder()
                    .quizId(Long.valueOf(quizId))
                    .player(playerId)
                    .correctAnswers(correctAnswers)
                    .uploadAnswer(new Date())
                    .build());

            updateBadgeEntity(playerId);
        }
        return correctAnswers;
    }

    private void updateBadgeEntity(String playerId) {
        PlayerEntity playerEntity = playerRepository.getReferenceById(playerId);
        BadgeEntity badgeEntity = badgeRepository.findOffensiveBadgeByPlayer(playerEntity);
        badgeEntity.setExecutedToday(badgeEntity.getExecutedToday() + 1);
        if (badgeEntity.getExecutedToday().compareTo(badgeEntity.getLevel()) == 0) {
            badgeEntity.setLevel(badgeEntity.getLevel() + 1);
            badgeEntity.setValid(false);
        }
        badgeRepository.save(badgeEntity);
    }
}
