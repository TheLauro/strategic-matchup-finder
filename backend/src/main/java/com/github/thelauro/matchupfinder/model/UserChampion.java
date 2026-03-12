package com.github.thelauro.matchupfinder.model;


import com.github.thelauro.matchupfinder.model.enums.Lane;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_champions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uniqueChampion",
                        columnNames = {"user_id", "champion_id", "lane"}
                )
        })
public class UserChampion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "champion_id")
    private Champion champion;

    @Enumerated(EnumType.STRING)
    private Lane lane;
}
