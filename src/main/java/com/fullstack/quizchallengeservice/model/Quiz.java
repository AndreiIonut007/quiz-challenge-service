package com.fullstack.quizchallengeservice.model;

import com.fullstack.quizchallengeservice.entity.Question;
import com.fullstack.quizchallengeservice.entity.Rewards;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {
    private Long id;

    @NotEmpty
    private String creator;

    @NotEmpty
    private String title;

    @NotEmpty
    private String timer;

    private Date expDate;

    private Rewards rewards;
    private List<Question> questions;
}
