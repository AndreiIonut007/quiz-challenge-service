package com.fullstack.quizchallengeservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BadgeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private Integer level;
    private Integer executedToday;
    private LocalDate lastUpdate;
    private boolean valid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "player_id",
            referencedColumnName = "email")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PlayerEntity player;
}
