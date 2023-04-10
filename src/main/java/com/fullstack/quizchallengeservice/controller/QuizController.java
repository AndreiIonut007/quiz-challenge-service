package com.fullstack.quizchallengeservice.controller;

import com.fullstack.quizchallengeservice.model.Quiz;
import com.fullstack.quizchallengeservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping
    public Quiz addQuiz(@Validated @RequestBody Quiz quiz) {
        return quizService.addQuiz(quiz);
    }

    @GetMapping
    public List<Quiz> selectQuizzes(@RequestParam String email) {
        return quizService.getAvailableQuizzes(email);
    }

    @GetMapping("/{id}")
    public Quiz getQuizById(@PathVariable String id){
        return quizService.getQuiById(id);
    }

    @PostMapping("/verify")
    public Integer saveAnswers(@RequestBody Boolean[][] checkboxArray, @RequestParam String quizId, @RequestParam String playerId){
        return quizService.saveAnswers(checkboxArray, quizId, playerId);
    }
}