package com.github.thelauro.matchupfinder.service;

import com.github.thelauro.matchupfinder.dto.ChampionDTO;
import com.github.thelauro.matchupfinder.dto.UserDTO;
import com.github.thelauro.matchupfinder.model.Champion;
import com.github.thelauro.matchupfinder.model.User;
import com.github.thelauro.matchupfinder.repository.ChampionRepository;
import com.github.thelauro.matchupfinder.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ChampionRepository championRepository;

    private final Long DEFAULT_USER_ID = 1L;

    public UserService(UserRepository userRepository, ChampionRepository championRepository) {
        this.userRepository = userRepository;
        this.championRepository = championRepository;
    }

    public List<ChampionDTO> getUserPool(){
        return userRepository.findUserPool(DEFAULT_USER_ID).stream().map(ChampionDTO::new).toList();
    }

    public void insertChampionOnPool(Long championId){
        User user = userRepository.getReferenceById(DEFAULT_USER_ID);
        Champion champion = championRepository.getReferenceById(championId);

        user.getUserChampions().add(champion);

        userRepository.save(user);
    }


    public void removeChampionFromPool(Long championId){
        userRepository.removeChampionFromUserPool(DEFAULT_USER_ID, championId);
    }

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream().map(UserDTO::new).toList();
    }

    public UserDTO getUser(){
        return userRepository.findById(DEFAULT_USER_ID).map(UserDTO::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public void insertUser(UserDTO data){
        userRepository.save(data.toEntity());
    }

}
