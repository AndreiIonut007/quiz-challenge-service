package com.fullstack.quizchallengeservice.repository;

import com.fullstack.quizchallengeservice.entity.QuizEntity;
import com.fullstack.quizchallengeservice.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface QuizRepository extends JpaRepository<QuizEntity, Long> {

    List<QuizEntity> findByCreatorNot(String email);

    @Query("select q from QuizEntity q where q.expDate <=:date")
    Set<QuizEntity> findExpiredQuizzes(Date date);
}
