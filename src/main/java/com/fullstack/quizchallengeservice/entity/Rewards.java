package com.fullstack.quizchallengeservice.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Rewards {
    private Long winner;
    private Long secPlace;
    private Long thirdPlace;
}
