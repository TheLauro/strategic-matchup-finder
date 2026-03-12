package com.github.thelauro.matchupfinder.service;

import com.github.thelauro.matchupfinder.dto.ChampionDTO;
import com.github.thelauro.matchupfinder.model.Champion;
import com.github.thelauro.matchupfinder.model.User;
import com.github.thelauro.matchupfinder.model.UserChampion;
import com.github.thelauro.matchupfinder.model.enums.Lane;
import com.github.thelauro.matchupfinder.repository.ChampionRepository;
import com.github.thelauro.matchupfinder.repository.UserChampionRepository;
import com.github.thelauro.matchupfinder.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.thelauro.matchupfinder.service.UserService.DEFAULT_USER_ID;

@Service
public class UserChampionService {

    private final UserRepository userRepository;
    private final UserChampionRepository userChampionRepository;
    private final ChampionRepository championRepository;

    public UserChampionService(UserRepository userRepository, UserChampionRepository userChampionRepository, ChampionRepository championRepository) {
        this.userRepository = userRepository;
        this.userChampionRepository = userChampionRepository;
        this.championRepository = championRepository;
    }

    public List<ChampionDTO> getUserPool(Lane lane){
        return userChampionRepository.findUserPool(DEFAULT_USER_ID, lane.name()).stream().map(ChampionDTO::new).toList();
    }

    public void insertChampionOnPool(Long championId, Lane lane){
        User user = userRepository.getReferenceById(DEFAULT_USER_ID);
        Champion champion = championRepository.getReferenceById(championId);

        UserChampion userChampion = new UserChampion(null, user, champion, lane);

        userChampionRepository.save(userChampion);
    }


    public void removeChampionFromPool(Long championId, Lane lane){
        userRepository.removeChampionFromUserPool(DEFAULT_USER_ID, championId, lane.name());
    }
}
