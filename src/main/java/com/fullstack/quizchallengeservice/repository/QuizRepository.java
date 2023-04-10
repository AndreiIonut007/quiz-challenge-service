package com.fullstack.quizchallengeservice.repository;

import com.fullstack.quizchallengeservice.entity.QuizEntity;
import com.fullstack.quizchallengeservice.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<QuizEntity, Long> {

    List<QuizEntity> findByCreatorNot(String email);
}
