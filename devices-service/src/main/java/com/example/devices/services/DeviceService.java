package com.example.devices.services;


import com.example.devices.dtos.devices.DeviceDTO;
import com.example.devices.dtos.devices.DeviceDetailsDTO;
import com.example.devices.dtos.devices.builders.DeviceBuilder;
import com.example.devices.entities.Device;
import com.example.devices.repositories.DeviceRepository;
import com.example.devices.handlers.exceptions.model.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<DeviceDTO> findDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    // Add this method
    public List<DeviceDTO> findAllByUserId(UUID userId) {
        return deviceRepository.findAll().stream()
                .filter(d -> d.getUser() != null && d.getUser().getUserId().equals(userId))
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDetailsDTO findDeviceById(UUID id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (deviceOptional.isEmpty()) {
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return DeviceBuilder.toDeviceDetailsDTO(deviceOptional.get());
    }

    public UUID insert(DeviceDetailsDTO deviceDetailsDTO) {
        Device device = DeviceBuilder.toEntity(deviceDetailsDTO);
        device = deviceRepository.save(device);
        LOGGER.debug("Device with id {} was inserted in db", device.getId());
        return device.getId();
    }

    // --- ADDED UPDATE METHOD ---
    @Transactional
    public DeviceDetailsDTO update(UUID id, DeviceDetailsDTO dto) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Device with id {} was not found in db", id);
                    return new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
                });

        device.setName(dto.getName());
        device.setConsumption(dto.getConsumption());
        device.setActive(dto.isActive());

        return DeviceBuilder.toDeviceDetailsDTO(deviceRepository.save(device));
    }

    // --- ADDED DELETE METHOD ---
    public void delete(UUID id) {
        if (!deviceRepository.existsById(id)) {
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        deviceRepository.deleteById(id);
        LOGGER.debug("Device with id {} was deleted", id);
    }

}
