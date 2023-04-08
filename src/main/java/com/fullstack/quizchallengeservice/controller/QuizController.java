package com.fullstack.quizchallengeservice.controller;

import com.fullstack.quizchallengeservice.model.Quiz;
import com.fullstack.quizchallengeservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping
    public Quiz addQuiz(@RequestBody Quiz quiz){
        return quizService.addQuiz(quiz);
    }
}
