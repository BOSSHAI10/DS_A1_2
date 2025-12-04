package com.example.devices.controllers;

import com.example.devices.dtos.devices.DeviceDTO;
import com.example.devices.dtos.devices.DeviceDetailsDTO;
import com.example.devices.services.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
@Validated
public class DeviceController {

    private final DeviceService deviceService;

    /* public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }
    */
    /*
    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        return ResponseEntity.ok(deviceService.findDevices());
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody DeviceDetailsDTO deviceDetailsDTO) {
        UUID id = deviceService.insert(deviceDetailsDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build(); // 201 + Location header
    }
    */

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDetailsDTO> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(deviceService.findDeviceById(id));
    }

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    // Admin: Get ALL devices
    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        return ResponseEntity.ok(deviceService.findDevices());
    }

    // Client: Get ONLY their devices
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DeviceDTO>> getDevicesByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(deviceService.findAllByUserId(userId));
    }

    // Fix the method name here (was getUser)
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDetailsDTO> getDevice(@PathVariable UUID id) {
        return ResponseEntity.ok(deviceService.findDeviceById(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody DeviceDetailsDTO deviceDetailsDTO) {
        UUID id = deviceService.insert(deviceDetailsDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    // --- ADDED UPDATE ENDPOINT ---
    @PutMapping("/{id}")
    public ResponseEntity<DeviceDetailsDTO> update(@PathVariable UUID id, @Valid @RequestBody DeviceDetailsDTO dto) {
        return ResponseEntity.ok(deviceService.update(id, dto));
    }

    // --- ADDED DELETE ENDPOINT ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
