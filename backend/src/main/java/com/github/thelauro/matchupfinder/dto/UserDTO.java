package com.github.thelauro.matchupfinder.dto;

import com.github.thelauro.matchupfinder.model.User;
import org.springframework.lang.NonNull;

public record UserDTO(Long id, String name) {

    public UserDTO(@NonNull User user){
        this(user.getId(),user.getName());
    }

    public User toEntity(){
        return new User(null, this.name);
    }
}
