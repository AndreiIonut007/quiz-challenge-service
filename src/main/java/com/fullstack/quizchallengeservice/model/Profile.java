package com.fullstack.quizchallengeservice.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @NotEmpty
    private String email;

    @NotEmpty
    private String icon;
    @NotEmpty
    private String name;


}
