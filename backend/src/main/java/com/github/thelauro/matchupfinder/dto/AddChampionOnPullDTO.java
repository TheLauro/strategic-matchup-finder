package com.github.thelauro.matchupfinder.dto;

import com.github.thelauro.matchupfinder.model.enums.Lane;

public record AddChampionOnPullDTO(Long championId, Lane lane) {
}
