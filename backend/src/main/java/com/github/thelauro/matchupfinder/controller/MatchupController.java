package com.github.thelauro.matchupfinder.controller;

import com.github.thelauro.matchupfinder.dto.MatchupInputDTO;
import com.github.thelauro.matchupfinder.dto.MatchupOutputDTO;
import com.github.thelauro.matchupfinder.model.enums.Lane;
import com.github.thelauro.matchupfinder.service.MatchupService;
import com.github.thelauro.matchupfinder.service.ScrapperService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matchups")
public class MatchupController {

    private final MatchupService matchupService;
    private final ScrapperService scrapperService;

    public MatchupController(MatchupService matchupService, ScrapperService scrapperService) {
        this.matchupService = matchupService;
        this.scrapperService = scrapperService;
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

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update")
    public void updateMatchups(){
        scrapperService.scrapMatchups();
    }
}
