package com.github.thelauro.matchupfinder.repository;

import com.github.thelauro.matchupfinder.model.Champion;
import com.github.thelauro.matchupfinder.model.Matchup;
import com.github.thelauro.matchupfinder.model.enums.Lane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MatchupRepository extends JpaRepository<Matchup, Long> {

    @Query(value = """
        select m from Matchup m 
        join fetch m.myChampion c 
        join fetch m.enemyChampion e 
        where c.id = :myChampionId 
        and e.id = :enemyChampionId
        and m.lane = :lane
""")
    Optional<Matchup> findByMyChampionIdAndEnemyChampionIdAndLane(
            @Param("myChampionId")Long myChampionId,
            @Param("enemyChampionId")Long enemyChampionId,
            @Param("lane")Lane lane);

    @Query(value = """
        select m from Matchup m 
        join fetch m.myChampion
        join fetch m.enemyChampion e 
        where e.id = :enemyChampionId
        and m.lane = :lane
""")
    List<Matchup> findByEnemyChampionIdAndLane(
            @Param("enemyChampionId")Long enemyChampionId,
            @Param("lane")Lane lane);


    Optional<Matchup> findByMyChampionAndEnemyChampionAndLane(
            @Param("myChampion")Champion myChampion,
            @Param("enemyChampion")Champion enemyChampion,
            @Param("lane")Lane lane);

    @Modifying
    @Query(value = """
        delete from Matchup m where m.lastUpdate < :scrapStart
""")
    void deleteByLastUpdateBefore(
            @Param("scrapStart")LocalDateTime scrapStart);
}
