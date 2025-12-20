package com.github.thelauro.matchupfinder.model;

import com.github.thelauro.matchupfinder.model.enums.Lane;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Matchup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hero_id")
    private Champion myChampion;

    @ManyToOne
    @JoinColumn(name = "enemy_id")
    private Champion enemyChampion;

    @Enumerated(EnumType.STRING)
    private Lane lane;
    private Double winRate;
    private Integer gamesPlayed;

    protected Matchup(){
    }

    public Matchup(Long id, Champion myChampion, Champion enemyChampion, Lane lane, Double winRate, Integer gamesPlayed) {
        this.id = id;
        this.myChampion = myChampion;
        this.enemyChampion = enemyChampion;
        this.lane = lane;
        this.winRate = winRate;
        this.gamesPlayed = gamesPlayed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Champion getMyChampion() {
        return myChampion;
    }

    public void setMyChampion(Champion myChampion) {
        this.myChampion = myChampion;
    }

    public Champion getEnemyChampion() {
        return enemyChampion;
    }

    public void setEnemyChampion(Champion enemyChampion) {
        this.enemyChampion = enemyChampion;
    }

    public Lane getLane() {
        return lane;
    }

    public void setLane(Lane lane) {
        this.lane = lane;
    }

    public Double getWinRate() {
        return winRate;
    }

    public void setWinRate(Double winRate) {
        this.winRate = winRate;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Matchup matchup)) return false;
        return Objects.equals(id, matchup.id) && Objects.equals(myChampion, matchup.myChampion) && Objects.equals(enemyChampion, matchup.enemyChampion) && Objects.equals(lane, matchup.lane) && Objects.equals(winRate, matchup.winRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, myChampion, enemyChampion, lane, winRate);
    }
}
