package com.fullstack.quizchallengeservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class PlayerEntity {
    @Id
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Long tokens;

}
