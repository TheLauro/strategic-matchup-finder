package com.github.thelauro.matchupfinder.service;

import com.github.thelauro.matchupfinder.dto.ChampionDTO;
import com.github.thelauro.matchupfinder.model.Champion;
import com.github.thelauro.matchupfinder.repository.ChampionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ChampionService {

    private final ChampionRepository championRepository;

    public ChampionService(ChampionRepository championRepository) {
        this.championRepository = championRepository;
    }

    public List<ChampionDTO> getAllChampions(){
        return championRepository.findAll().stream().map(ChampionDTO::new).toList();
    }

    public ChampionDTO getChampion(Long id){
        return championRepository.findById(id).map(ChampionDTO::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Campeão não encontrado"));
    }

    public void insertChampion(ChampionDTO data){
        championRepository.save(data.toEntity());
    }
}
