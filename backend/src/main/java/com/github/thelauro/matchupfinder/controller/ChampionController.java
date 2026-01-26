package com.github.thelauro.matchupfinder.controller;

import com.github.thelauro.matchupfinder.dto.ChampionDTO;
import com.github.thelauro.matchupfinder.service.ChampionService;
import com.github.thelauro.matchupfinder.service.ScrapperService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/champions")
public class ChampionController {

    private final ChampionService championService;
    private final ScrapperService scrapperService;

    public ChampionController(ChampionService championService, ScrapperService scrapperService) {
        this.championService = championService;
        this.scrapperService = scrapperService;
    }

    @GetMapping
    public List<ChampionDTO> getAllChampions(){
        return championService.getAllChampions();
    }

    @GetMapping("/{id}")
    public ChampionDTO getChampion(@PathVariable Long id){
        return championService.getChampion(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void upsertChampions(){
        scrapperService.scrapChampions();
    }
}
