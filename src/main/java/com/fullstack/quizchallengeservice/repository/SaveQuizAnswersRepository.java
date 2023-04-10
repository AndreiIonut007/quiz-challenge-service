package com.fullstack.quizchallengeservice.repository;

import com.fullstack.quizchallengeservice.entity.SaveQuizAnswersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaveQuizAnswersRepository extends JpaRepository<SaveQuizAnswersEntity, Long> {
    List<SaveQuizAnswersEntity> findAllByPlayer(String email);
}
