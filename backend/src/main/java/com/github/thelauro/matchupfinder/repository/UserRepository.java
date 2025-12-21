package com.github.thelauro.matchupfinder.repository;

import com.github.thelauro.matchupfinder.model.Champion;
import com.github.thelauro.matchupfinder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
        SELECT champ.* FROM champion champ
        INNER JOIN user_champions pools ON champ.id = pools.champion_id
        WHERE pools.user_id = :userId
        """, nativeQuery = true)
    public List<Champion> findUserPool(@Param("userId") Long userId);

    @Query(value= """
        DELETE FROM user_champions pools
        WHERE pools.user_id =:userId 
        AND pools.champion_id = :championId
        """, nativeQuery = true)
    public void removeChampionFromUserPool(@Param("userId")Long userId, @Param("championId")Long championId);
}
