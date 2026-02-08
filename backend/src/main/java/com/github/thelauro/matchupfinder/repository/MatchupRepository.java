package com.github.thelauro.matchupfinder.repository;

import com.github.thelauro.matchupfinder.model.Champion;
import com.github.thelauro.matchupfinder.model.Matchup;
import com.github.thelauro.matchupfinder.model.enums.Lane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MatchupRepository extends JpaRepository<Matchup, Long> {
    Optional<Matchup> findByMyChampionIdAndEnemyChampionIdAndLane(Long myChampionId, Long enemyChampionId, Lane lane);

    List<Matchup> findByEnemyChampionId(Long enemyChampionId);

    List<Matchup> findByEnemyChampionIdAndLane(Long enemyChampionId, Lane lane);

    Optional<Matchup> findByMyChampionAndEnemyChampionAndLane(Champion myChampion, Champion enemyChampion, Lane lane);
}
