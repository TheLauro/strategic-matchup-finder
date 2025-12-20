package com.github.thelauro.matchupfinder.controller;

import com.github.thelauro.matchupfinder.dto.ChampionDTO;
import com.github.thelauro.matchupfinder.service.ChampionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/champions")
public class ChampionController {

    private final ChampionService championService;

    public ChampionController(ChampionService championService) {
        this.championService = championService;
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
    public void insertChampion(@RequestBody ChampionDTO data){
        championService.insertChampion(data);
    }
}
