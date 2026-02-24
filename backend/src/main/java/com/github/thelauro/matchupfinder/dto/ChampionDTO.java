package com.github.thelauro.matchupfinder.dto;

import com.github.thelauro.matchupfinder.model.Champion;
import org.springframework.lang.NonNull;

public record ChampionDTO(Long id, String name, String iconUrl, String splashArtUrl, String mostCommonLane) {

    public ChampionDTO(String name, String iconUrl, String splashArtUrl, String mostCommonLane) {
        this(null, name, iconUrl, splashArtUrl, mostCommonLane);
    }

    public ChampionDTO(@NonNull Champion champion){
        this(champion.getId(), champion.getName(), champion.getIconUrl(), champion.getSplashArtUrl(), champion.getMostCommonLane().name());
    }

    public Champion toEntity(){
        return new Champion(null,this.name,this.iconUrl,this.splashArtUrl,null);
    }
}
