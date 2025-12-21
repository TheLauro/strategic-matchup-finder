package com.github.thelauro.matchupfinder.dto;

import com.github.thelauro.matchupfinder.model.Champion;
import org.springframework.lang.NonNull;

public record ChampionDTO(Long id, String name, String imageUrl) {

    public ChampionDTO(@NonNull Champion champion){
        this(champion.getId(), champion.getName(), champion.getImageUrl());
    }

    public Champion toEntity(){
        return new Champion(this.imageUrl,this.name,null);
    }
}
