package com.fullstack.quizchallengeservice.repository;

import com.fullstack.quizchallengeservice.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {
}
