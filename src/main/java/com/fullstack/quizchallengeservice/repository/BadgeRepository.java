package com.fullstack.quizchallengeservice.repository;

import com.fullstack.quizchallengeservice.entity.BadgeEntity;
import com.fullstack.quizchallengeservice.entity.PlayerEntity;
import com.fullstack.quizchallengeservice.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface BadgeRepository extends JpaRepository<BadgeEntity,Long> {

    Set<BadgeEntity> findAllByPlayer(PlayerEntity player);
}
