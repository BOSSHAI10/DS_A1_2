package com.example.auth.dtos.credentials;


import com.example.auth.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CredentialsDetailsDTO {
    @NotBlank(message = "email is required")
    @Email(message = "invalid email")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    private Role role; // Add this

    public CredentialsDetailsDTO() {}

    public CredentialsDetailsDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public CredentialsDetailsDTO(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role;}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
