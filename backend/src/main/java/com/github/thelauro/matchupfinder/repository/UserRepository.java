package com.github.thelauro.matchupfinder.repository;

import com.github.thelauro.matchupfinder.model.Champion;
import com.github.thelauro.matchupfinder.model.User;
import com.github.thelauro.matchupfinder.model.enums.Lane;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {



    @Modifying
    @Transactional
    @Query(value= """
        DELETE FROM user_champions pools
        WHERE pools.user_id =:userId 
        AND pools.champion_id = :championId
        AND pools.lane = :lane
        """, nativeQuery = true)
    public void removeChampionFromUserPool(@Param("userId")Long userId, @Param("championId")Long championId, @Param("lane") String lane);
}
