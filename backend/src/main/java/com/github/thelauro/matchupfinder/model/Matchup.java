package com.github.thelauro.matchupfinder.model;

import com.github.thelauro.matchupfinder.model.enums.Lane;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Matchup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hero_id")
    private Champion myChampion;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enemy_id")
    private Champion enemyChampion;

    @Enumerated(EnumType.STRING)
    private Lane lane;
    private Double winRate;
    private Integer gamesPlayed;
    private LocalDateTime lastUpdate;

}
