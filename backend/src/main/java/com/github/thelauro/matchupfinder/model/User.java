package com.github.thelauro.matchupfinder.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "user_champions",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "champion_id")
    )
    private List<Champion> UserChampions = new ArrayList<>();

    protected User() {
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Champion> getUserChampions() {
        return UserChampions;
    }

    public void setUserChampions(List<Champion> userChampions) {
        UserChampions = userChampions;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(UserChampions, user.UserChampions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, UserChampions);
    }
}