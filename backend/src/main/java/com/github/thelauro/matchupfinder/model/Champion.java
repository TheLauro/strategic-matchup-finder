package com.github.thelauro.matchupfinder.model;

import com.github.thelauro.matchupfinder.model.enums.Lane;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Champion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String iconUrl;
    private String splashArtUrl;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Lane mostCommonLane;
}
