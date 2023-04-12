package com.fullstack.quizchallengeservice.service;

import com.fullstack.quizchallengeservice.model.Player;

import java.util.List;
import java.util.Optional;

public interface ProfileService {

    Player configProfile(Player player);

    Boolean validateQuizCreation(String id);
    Boolean validateQuizSelection(String id);

    List<Player> getRanking();
}
