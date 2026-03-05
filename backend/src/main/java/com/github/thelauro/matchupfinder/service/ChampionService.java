package com.github.thelauro.matchupfinder.service;

import com.github.thelauro.matchupfinder.dto.ChampionDTO;
import com.github.thelauro.matchupfinder.model.Champion;
import com.github.thelauro.matchupfinder.model.enums.Lane;
import com.github.thelauro.matchupfinder.repository.ChampionRepository;
import com.github.thelauro.matchupfinder.repository.MatchupRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ChampionService {

    private final ChampionRepository championRepository;
    private final MatchupRepository matchupRepository;

    public ChampionService(ChampionRepository championRepository, MatchupRepository matchupRepository) {
        this.championRepository = championRepository;
        this.matchupRepository = matchupRepository;
    }

    public List<ChampionDTO> getAllChampions(){
        return championRepository.findAll().stream().map(ChampionDTO::new).toList();
    }

    public ChampionDTO getChampion(Long id){
        return championRepository.findById(id).map(ChampionDTO::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Campeão não encontrado"));
    }

    public List<ChampionDTO> getChampionByMostCommonLane(Lane lane){

        return championRepository.findAllByMostCommonLaneOrderByNameAsc(lane).stream().map(ChampionDTO::new).toList();
    }


    @Modifying
    public void updateChampionsCommonLane(){

        List<Champion> champions = championRepository.findAll();

        for(Champion champion : champions) {

            List<Lane> result = matchupRepository.findMostCommonLaneByMyChampionId(champion.getId());

            Lane mostCommonLane = result.get(0);

            champion.setMostCommonLane(mostCommonLane);

            championRepository.save(champion);
        }
    }
}
