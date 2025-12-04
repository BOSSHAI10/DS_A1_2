package com.example.devices.services;

import com.example.devices.dtos.users.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Service
public class UsersServiceClient {

    private final RestTemplate restTemplate;

    // URL-ul serviciului de utilizatori (din docker-compose sau localhost)
    @Value("${users.service.url:http://users-service:8081/users}")
    private String usersServiceUrl;

    public UsersServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getUserEmailById(UUID userId) {
        try {
            // 1. Pregătim Header-ul cu Token-ul curent (al Adminului care face cererea)
            HttpHeaders headers = new HttpHeaders();
            String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest().getHeader("Authorization");

            if (token != null) {
                headers.set("Authorization", token);
            }

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            // 2. Facem apelul GET către /users/{id}
            UserDTO userDto = restTemplate.exchange(
                    usersServiceUrl + "/" + userId,
                    HttpMethod.GET,
                    entity,
                    UserDTO.class
            ).getBody();

            return (userDto != null) ? userDto.getEmail() : null;

        } catch (Exception e) {
            // Logăm eroarea și returnăm un fallback sau aruncăm excepția mai departe
            System.err.println("Could not fetch user email: " + e.getMessage());
            return "unknown@user.com"; // Fallback mai bun decât placeholder, dar tot indică o eroare
        }
    }
}