package com.example.devices.dtos.users;

import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String email;
    // Poți adăuga și alte câmpuri dacă e nevoie, dar email e suficient

    public UserDTO() {}

    public UserDTO(UUID id, String email) {
        this.id = id;
        this.email = email;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}