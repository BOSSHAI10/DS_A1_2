package com.example.devices.controllers;

import com.example.devices.entities.Device;
import com.example.devices.entities.LinkedUser;
import com.example.devices.entities.Role;
import com.example.devices.repositories.DeviceRepository;
import com.example.devices.repositories.LinkedUserRepository;
import com.example.devices.services.UsersServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/linked_user") // Specific path for associations
public class LinkedUserController {

    private final DeviceRepository deviceRepository;
    private final LinkedUserRepository linkedUserRepository;
    private final UsersServiceClient usersServiceClient; // Injectăm clientul

    public LinkedUserController(DeviceRepository deviceRepository,
                                LinkedUserRepository linkedUserRepository,
                                UsersServiceClient usersServiceClient) {
        this.deviceRepository = deviceRepository;
        this.linkedUserRepository = linkedUserRepository;
        this.usersServiceClient = usersServiceClient;
    }

    @PostMapping("/{userId}/{deviceId}")
    public ResponseEntity<Void> assignDevice(@PathVariable UUID userId, @PathVariable UUID deviceId) {

        // 1. Căutăm utilizatorul local sau îl creăm FETCH-uind datele reale
        LinkedUser user = linkedUserRepository.findAll().stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElseGet(() -> {
                    // AICI ESTE SCHIMBAREA: Cerem email-ul real de la users-service
                    String realEmail = usersServiceClient.getUserEmailById(userId);

                    LinkedUser newUser = new LinkedUser(userId, realEmail);
                    newUser.setRole(Role.USER);
                    return linkedUserRepository.save(newUser);
                });

        // 2. Găsim Device-ul
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        // 3. Facem legătura
        device.setUser(user);
        deviceRepository.save(device);

        return ResponseEntity.ok().build();
    }
}