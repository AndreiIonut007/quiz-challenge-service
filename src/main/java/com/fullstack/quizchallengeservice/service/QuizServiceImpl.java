package com.fullstack.quizchallengeservice.service;

import com.fullstack.quizchallengeservice.entity.QuizEntity;
import com.fullstack.quizchallengeservice.model.Quiz;
import com.fullstack.quizchallengeservice.repository.QuizRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService{

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public Quiz addQuiz(Quiz quiz) {
        QuizEntity quizEntity = new QuizEntity();
        BeanUtils.copyProperties(quiz, quizEntity);
        quizEntity = quizRepository.save(quizEntity);
        quiz.setId(quizEntity.getId());
        return quiz;
    }
}
