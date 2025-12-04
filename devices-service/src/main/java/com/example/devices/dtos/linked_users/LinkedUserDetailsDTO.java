package com.example.devices.dtos.linked_users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

public class LinkedUserDetailsDTO {

    private UUID linkId;

    private UUID userId;

    @NotBlank(message = "email is required")
    @Email (message = "email is invalid")
    private String email;

    public LinkedUserDetailsDTO() {}

    public LinkedUserDetailsDTO(String email) {

        this.email = email;
    }

    public LinkedUserDetailsDTO(UUID userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public LinkedUserDetailsDTO(UUID linkId, UUID userId, String email) {
        this.linkId = linkId;
        this.userId = userId;
        this.email = email;
    }

    // getters / setters

    public UUID getLinkId() { return linkId; }
    public void setLinkId(UUID linkId) { this.linkId = linkId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedUserDetailsDTO that = (LinkedUserDetailsDTO) o;
        return Objects.equals(linkId, that.linkId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linkId, userId, email);
    }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
}
