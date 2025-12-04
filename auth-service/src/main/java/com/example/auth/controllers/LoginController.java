package com.example.auth.controllers;

import com.example.auth.dtos.credentials.CredentialsDetailsDTO;
import com.example.auth.entities.Credentials;
import com.example.auth.components.security.JwtUtil;
import com.example.auth.services.CredentialsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Validated
public class LoginController {

    private final CredentialsService credentialsService;
    private final JwtUtil jwtUtil;

    public LoginController(CredentialsService credentialsService, JwtUtil jwtUtil) {
        this.credentialsService = credentialsService;
        this.jwtUtil = jwtUtil;
    }

    /* @PostMapping("/login")
    public ResponseEntity<Credentials> login(@Valid @Email String email, @Valid String password) {
        return ResponseEntity.ok(credentialsService.login(email, password));
    } */

    /* @PostMapping("/login")
    public ResponseEntity<Boolean> login(@Valid @RequestBody CredentialsDetailsDTO credentialsDetailsDTO) {
        return ResponseEntity.ok(credentialsService.verify(credentialsDetailsDTO.getEmail(), credentialsDetailsDTO.getPassword()));
    } */

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody CredentialsDetailsDTO dto) {
        // 1. Authenticate
        Credentials user = credentialsService.login(dto.getEmail(), dto.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // 2. Generate Token passing the Enum name (e.g., "ADMIN")
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getId().toString(),
                user.getRole().name() // <--- Convert Enum to String here
        );

        return ResponseEntity.ok(token);
    }
}
