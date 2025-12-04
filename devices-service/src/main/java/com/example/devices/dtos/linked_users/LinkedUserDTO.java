package com.example.devices.dtos.linked_users;

import java.util.Objects;
import java.util.UUID;

public class LinkedUserDTO {
    private UUID userId;

    private String email;

    public LinkedUserDTO() {}
    public LinkedUserDTO(UUID userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedUserDTO that = (LinkedUserDTO) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() { return Objects.hash(userId, email); }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
