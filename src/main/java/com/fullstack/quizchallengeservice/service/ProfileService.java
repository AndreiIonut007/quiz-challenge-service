package com.fullstack.quizchallengeservice.service;

import com.fullstack.quizchallengeservice.model.Player;

import java.util.Optional;

public interface ProfileService {

    Optional<Player> getProfile(String id);

    Player configProfile(Player player);
}
