package com.fullstack.quizchallengeservice.controller;

import com.fullstack.quizchallengeservice.model.Profile;
import com.fullstack.quizchallengeservice.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PutMapping
    public Profile configProfile(@Validated @RequestBody Profile profile){
        return profileService.configProfile(profile);
    }

    @GetMapping
    public Optional<Profile> getProfile(@RequestParam String id){
        return profileService.getProfile(id);
    }

}
