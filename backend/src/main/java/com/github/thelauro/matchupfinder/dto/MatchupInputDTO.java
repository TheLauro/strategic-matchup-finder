package com.github.thelauro.matchupfinder.dto;

import com.github.thelauro.matchupfinder.model.enums.Lane;

public record MatchupInputDTO(Long id, Long heroId, Long enemyId, Lane lane, Double winRate, Integer gamesPlayed) {
}
