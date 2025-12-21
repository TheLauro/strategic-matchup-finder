package com.github.thelauro.matchupfinder.controller;

import com.github.thelauro.matchupfinder.dto.AddChampionOnPullDTO;
import com.github.thelauro.matchupfinder.dto.ChampionDTO;
import com.github.thelauro.matchupfinder.dto.UserDTO;
import com.github.thelauro.matchupfinder.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
    public List<ChampionDTO> getUserPool(){
        return userService.getUserPool();
    }

    @PostMapping("/me/pool")
    public void insertChampionOnPool(@RequestBody AddChampionOnPullDTO input){
        userService.insertChampionOnPool(input.championId());
    }

    @DeleteMapping("/me/pool/{id}")
    public void removeChampionOnPool (@PathVariable Long id){
        userService.removeChampionFromPool(id);
    }
}
