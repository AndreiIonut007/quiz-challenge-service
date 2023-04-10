package com.fullstack.quizchallengeservice.service;

import com.fullstack.quizchallengeservice.model.Quiz;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuizService {
    Quiz addQuiz(Quiz quiz);

    List<Quiz> getAvailableQuizzes(String email);

    Quiz getQuiById(String id);

    Integer saveAnswers(Boolean[][] checkboxArray, String quizId, String playerId);
}
