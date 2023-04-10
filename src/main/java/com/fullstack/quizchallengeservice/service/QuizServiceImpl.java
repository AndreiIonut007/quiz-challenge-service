package com.fullstack.quizchallengeservice.service;

import com.fullstack.quizchallengeservice.entity.Question;
import com.fullstack.quizchallengeservice.entity.QuizEntity;
import com.fullstack.quizchallengeservice.entity.SaveQuizAnswersEntity;
import com.fullstack.quizchallengeservice.model.Quiz;
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

    @Override
    public Quiz addQuiz(Quiz quiz) {
        QuizEntity quizEntity = new QuizEntity();
        BeanUtils.copyProperties(quiz, quizEntity);
        quizEntity = quizRepository.save(quizEntity);
        quiz.setId(quizEntity.getId());
        return quiz;
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
        }
        return correctAnswers;
    }
}
