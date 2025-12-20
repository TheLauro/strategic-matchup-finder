package com.github.thelauro.matchupfinder.dto;

import com.github.thelauro.matchupfinder.model.Champion;
import com.github.thelauro.matchupfinder.model.Matchup;
import com.github.thelauro.matchupfinder.model.enums.Lane;
import org.springframework.lang.NonNull;

public record MatchupOutputDTO(Long id, Champion myChampion, Champion enemyChampion, Lane lane, Double winRate, Integer gamesPlayed) {

    public MatchupOutputDTO(@NonNull Matchup matchup){
        this(matchup.getId(),matchup.getMyChampion(),matchup.getEnemyChampion(),matchup.getLane(), matchup.getWinRate(), matchup.getGamesPlayed());
    }
}
