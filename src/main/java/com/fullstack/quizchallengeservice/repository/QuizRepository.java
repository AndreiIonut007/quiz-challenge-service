package com.fullstack.quizchallengeservice.repository;

import com.fullstack.quizchallengeservice.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<QuizEntity, Long> {
}
