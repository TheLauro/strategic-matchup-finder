package com.github.thelauro.matchupfinder.repository;
import com.github.thelauro.matchupfinder.model.Champion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionRepository extends JpaRepository<Champion, Long> {
    boolean existsByName(String name);

    Champion findByName(String name);
}
