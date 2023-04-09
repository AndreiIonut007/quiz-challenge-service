package com.fullstack.quizchallengeservice.repository;

import com.fullstack.quizchallengeservice.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlayerRepository extends JpaRepository<PlayerEntity, String> {

    @Query("Select count(p.email) from PlayerEntity p where p.tokens > (Select pl.tokens from PlayerEntity pl where pl.email = ?1)" )
    Integer getRank(String email);
}
