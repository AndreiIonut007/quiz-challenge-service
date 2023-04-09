package com.fullstack.quizchallengeservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Question {
    private String question;
    private String description;
    private String image;
    private List<Boolean> answersTypes;
    private List<String> answersValues;

}
