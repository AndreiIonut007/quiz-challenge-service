package com.fullstack.quizchallengeservice.repository;

import com.fullstack.quizchallengeservice.entity.BadgeEntity;
import com.fullstack.quizchallengeservice.entity.PlayerEntity;
import com.fullstack.quizchallengeservice.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface BadgeRepository extends JpaRepository<BadgeEntity,Long> {

    Set<BadgeEntity> findAllByPlayer(PlayerEntity player);

    @Query("Select b from BadgeEntity b where b.player = ?1 and b.type = 'Offensive'")
    BadgeEntity findOffensiveBadgeByPlayer(PlayerEntity playerEntity);

    @Query("Select b from BadgeEntity b where b.player = ?1 and b.type = 'Defensive'")
    BadgeEntity findDefensiveBadgeByPlayer(PlayerEntity playerEntity);
}
