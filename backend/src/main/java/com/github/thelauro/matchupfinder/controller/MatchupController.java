package com.github.thelauro.matchupfinder.controller;

import com.github.thelauro.matchupfinder.dto.MatchupInputDTO;
import com.github.thelauro.matchupfinder.dto.MatchupOutputDTO;
import com.github.thelauro.matchupfinder.model.enums.Lane;
import com.github.thelauro.matchupfinder.service.MatchupService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matchups")
public class MatchupController {

    private final MatchupService matchupService;

    public MatchupController(MatchupService matchupService) {
        this.matchupService = matchupService;
    }

    @GetMapping
    public List<MatchupOutputDTO> getMatchups(
            @RequestParam(required = false) Long heroId,
            @RequestParam Long enemyId,
            @RequestParam Lane lane){

        if(heroId!=null) {
            MatchupOutputDTO matchup =  matchupService.getMatchup(heroId, enemyId, lane);
            return List.of(matchup);
        } else {
            return matchupService.getAllEnemyMatchupOnLane(enemyId,lane);
      }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void upsertMatchup(@RequestBody MatchupInputDTO data){
        matchupService.upsertMatchup(data);
    }
}
