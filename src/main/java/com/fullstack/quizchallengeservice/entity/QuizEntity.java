package com.fullstack.quizchallengeservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String creator;
    private String title;
    private String timer;
    private Date expDate;

    @Embedded
    private Rewards rewards;

    @ElementCollection
    private List<Question> questions;
}

