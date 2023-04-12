package com.fullstack.quizchallengeservice.controller;

import com.fullstack.quizchallengeservice.model.Player;
import com.fullstack.quizchallengeservice.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/ranking")
public class RankingController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public List<Player> getRanking(){
        return profileService.getRanking();
    }
}
