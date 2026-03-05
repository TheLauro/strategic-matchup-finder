package com.github.thelauro.matchupfinder.repository;
import com.github.thelauro.matchupfinder.model.Champion;
import com.github.thelauro.matchupfinder.model.enums.Lane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChampionRepository extends JpaRepository<Champion, Long> {
    boolean existsByName(String name);

    Champion findByName(String name);

    List<Champion> findAllByMostCommonLaneOrderByNameAsc(Lane mostCommonLane);

    List<Champion> findAllByOrderByNameAsc();
}
