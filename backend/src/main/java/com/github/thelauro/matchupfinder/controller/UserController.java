package com.github.thelauro.matchupfinder.controller;

import com.github.thelauro.matchupfinder.dto.AddChampionOnPullDTO;
import com.github.thelauro.matchupfinder.dto.ChampionDTO;
import com.github.thelauro.matchupfinder.dto.UserDTO;
import com.github.thelauro.matchupfinder.model.enums.Lane;
import com.github.thelauro.matchupfinder.service.UserChampionService;
import com.github.thelauro.matchupfinder.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserChampionService userChampionService;

    public UserController(UserService userService, UserChampionService userChampionService) {
        this.userService = userService;
        this.userChampionService = userChampionService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers(){

        return userService.getAllUsers();
    }

    @GetMapping("/me")
    public UserDTO getUser(){
        return userService.getUser();
    }

    @PostMapping
    public void insertUser(@RequestBody UserDTO data){
        userService.insertUser(data);
    }

    @GetMapping("/me/pool")
    public List<ChampionDTO> getUserPool(@RequestParam Lane lane){

        return userChampionService.getUserPool(lane);
    }

    @PostMapping("/me/pool")
    public void insertChampionOnPool(@RequestBody AddChampionOnPullDTO input){
        userChampionService.insertChampionOnPool(input.championId(), input.lane());
    }

    @DeleteMapping("/me/pool")
    public void removeChampionOnPool (@RequestParam Long championId, @RequestParam Lane lane){
        userChampionService.removeChampionFromPool(championId, lane);
    }
}
