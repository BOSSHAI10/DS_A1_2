package com.example.devices.dtos.linked_users.builders;

import com.example.devices.dtos.linked_users.LinkedUserDTO;
import com.example.devices.dtos.linked_users.LinkedUserDetailsDTO;
import com.example.devices.entities.LinkedUser;

public class LinkedUserBuilder {

    private LinkedUserBuilder() {
    }

    public static LinkedUserDTO toUserDTO(LinkedUser user) {
        return new LinkedUserDTO(user.getUserId(), user.getEmail());
    }

    public static LinkedUserDetailsDTO toUserDetailsDTO(LinkedUser user) {
        return new LinkedUserDetailsDTO(user.getUserId(), user.getEmail());
    }

    public static LinkedUser toEntity(LinkedUserDetailsDTO userDetailsDTO) {
        return new LinkedUser(userDetailsDTO.getUserId(),
                userDetailsDTO.getEmail());
    }
}
