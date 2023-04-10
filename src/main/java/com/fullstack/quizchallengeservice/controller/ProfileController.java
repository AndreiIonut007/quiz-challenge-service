package com.fullstack.quizchallengeservice.controller;

import com.fullstack.quizchallengeservice.model.Player;
import com.fullstack.quizchallengeservice.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PutMapping
    public Player configProfile(@Validated @RequestBody Player player){
        return profileService.configProfile(player);
    }

}
