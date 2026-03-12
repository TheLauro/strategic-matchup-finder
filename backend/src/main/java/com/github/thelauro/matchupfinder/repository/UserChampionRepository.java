package com.github.thelauro.matchupfinder.repository;

import com.github.thelauro.matchupfinder.model.Champion;
import com.github.thelauro.matchupfinder.model.UserChampion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserChampionRepository extends JpaRepository<UserChampion, Long> {

    @Query(value = """
        SELECT champ.* FROM champion champ
        INNER JOIN user_champions pools ON champ.id = pools.champion_id
        WHERE pools.user_id = :userId AND pools.lane = :lane
        ORDER BY champ.name ASC 
        """, nativeQuery = true)
    public List<Champion> findUserPool(@Param("userId") Long userId, @Param("lane") String lane);


}
