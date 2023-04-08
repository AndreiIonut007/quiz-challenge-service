package com.fullstack.quizchallengeservice.model;

import com.fullstack.quizchallengeservice.entity.Question;
import com.fullstack.quizchallengeservice.entity.Rewards;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Quiz {
    private Long id;
    private String creator;
    private String title;
    private String timer;
    private Date expirationDate;
    private Rewards rewards;
    private List<Question> questions;
}
