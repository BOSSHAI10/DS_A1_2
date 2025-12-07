package com.example.auth.controllers;

import com.example.auth.dtos.credentials.CredentialsDetailsDTO;
import com.example.auth.entities.Credentials;
import com.example.auth.services.CredentialsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Validated
public class RegisterController {
    private final CredentialsService credentialsService;

    public RegisterController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    /* @PostMapping("/register")
    public ResponseEntity<Credentials> register(@Valid @Email String email, String password) {
        return ResponseEntity.ok(credentialsService.register(email, password));
    } */

    /*
    @PostMapping("/register")
    public ResponseEntity<NewUser> register(@Valid @RequestBody NewUserDetailsDTO newUserDetailsDTO) {
        return ResponseEntity.ok(userService.register(newUserDetailsDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<NewUser> register(String name, @Valid @Email String email, @Valid @AgeLimit(value = 18) Integer age) {
        return ResponseEntity.ok(userService.register(name, email, age));
    }
    */

    // Keep ONLY this method for creating login accounts
    @PostMapping("/register")
    public ResponseEntity<Credentials> register(@Valid @RequestBody CredentialsDetailsDTO dto) {
        return ResponseEntity.ok(credentialsService.register(
                dto.getEmail(),
                dto.getPassword()
                ));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> delete(@PathVariable String email) {
        credentialsService.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }
}