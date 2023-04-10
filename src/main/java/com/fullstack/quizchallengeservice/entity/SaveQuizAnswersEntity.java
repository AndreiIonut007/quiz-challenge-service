package com.fullstack.quizchallengeservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveQuizAnswersEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String player;

    @Column(nullable = false)
    private Long quizId;

    @Column(nullable = false)
    private Integer correctAnswers;

    private Date uploadAnswer;
}
