package com.fullstack.quizchallengeservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ProfileEntity {
    @Id
    private String email;

    @Column(nullable = false)
    private String icon;

    @Column(nullable = false)
    private String name;

    @ColumnDefault("100")
    private Long tokens;

    @OneToMany(
            mappedBy = "player",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private Set<BadgesEntity> badges;
}
