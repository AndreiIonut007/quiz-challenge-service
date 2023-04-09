package com.fullstack.quizchallengeservice.model;

import com.fullstack.quizchallengeservice.entity.BadgeEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    @NotEmpty
    private String email;
    private String username;
    private Long tokens;
    private Set<BadgeEntity> badges;
    private Integer rank;


}
