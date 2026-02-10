package com.github.thelauro.matchupfinder.service;

import com.github.thelauro.matchupfinder.dto.MatchupInputDTO;
import com.github.thelauro.matchupfinder.dto.MatchupOutputDTO;
import com.github.thelauro.matchupfinder.model.Champion;
import com.github.thelauro.matchupfinder.model.Matchup;
import com.github.thelauro.matchupfinder.repository.ChampionRepository;
import com.github.thelauro.matchupfinder.repository.MatchupRepository;
import com.github.thelauro.matchupfinder.model.enums.Lane;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MatchupService {

    private final MatchupRepository matchupRepository;
    private final ChampionRepository championRepository;

    public MatchupService(MatchupRepository matchupRepository, ChampionRepository championRepository) {
        this.matchupRepository = matchupRepository;
        this.championRepository = championRepository;
    }

    public MatchupOutputDTO getMatchup(Long heroId, Long enemyId, Lane lane){
        return matchupRepository.findByMyChampionIdAndEnemyChampionIdAndLane(heroId,enemyId,lane).map(MatchupOutputDTO::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matchup não encontrada, dados insuficientes"));
    }

    public List<MatchupOutputDTO> getAllEnemyMatchupOnLane(Long id, Lane lane){
        return matchupRepository.findByEnemyChampionIdAndLane(id,lane).stream().map(MatchupOutputDTO::new).toList();
    }

}
