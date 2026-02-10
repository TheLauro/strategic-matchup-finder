package com.github.thelauro.matchupfinder.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Table(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_champions",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "champion_id")
    )
    private List<Champion> userChampions = new ArrayList<>();

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}