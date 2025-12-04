package com.example.devices.services;


import com.example.devices.dtos.linked_users.LinkedUserDTO;
import com.example.devices.dtos.linked_users.LinkedUserDetailsDTO;
import com.example.devices.dtos.linked_users.builders.LinkedUserBuilder;
import com.example.devices.entities.LinkedUser;
import com.example.devices.handlers.exceptions.model.ResourceNotFoundException;
import com.example.devices.repositories.LinkedUserRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LinkedUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkedUserService.class);
    private final LinkedUserRepository userRepository;


    @Autowired
    public LinkedUserService(LinkedUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<LinkedUserDTO> findUsers() {
        List<LinkedUser> userList = userRepository.findAll();
        return userList.stream()
                .map(LinkedUserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }

    public LinkedUserDetailsDTO findUserById(UUID id) {
        Optional<LinkedUser> prosumerOptional = userRepository.findById(id);
        if (prosumerOptional.isEmpty()) {
            LOGGER.error("Person with id {} was not found in db", id);
            throw new ResourceNotFoundException(LinkedUser.class.getSimpleName() + " with id: " + id);
        }
        return LinkedUserBuilder.toUserDetailsDTO(prosumerOptional.get());
    }

    public LinkedUserDetailsDTO findUserByEmail(String email) {
        Optional<LinkedUser> prosumerOptional = userRepository.findByEmail(email);
        if (prosumerOptional.isEmpty()) {
            LOGGER.error("Person with email {} was not found in db", email);
            throw new ResourceNotFoundException(LinkedUser.class.getSimpleName() + " with email: " + email);
        }
        return LinkedUserBuilder.toUserDetailsDTO(prosumerOptional.get());
    }

    @Transactional
    public UUID insert(LinkedUserDetailsDTO userDetailsDTO) {
        LinkedUser user = LinkedUserBuilder.toEntity(userDetailsDTO);
        user = userRepository.save(user);
        LOGGER.debug("Person with id {} was inserted in db", user.getUserId());
        return user.getUserId();
    }

    public boolean existsById (UUID id) {
        Optional<LinkedUser> prosumerOptional = userRepository.findById(id);
        return prosumerOptional.isPresent();
    }

    public boolean existsByEmail(String email) {
        Optional<LinkedUser> prosumerOptional = userRepository.findByEmail(email);
        return prosumerOptional.isPresent();
    }

    @Transactional
    public void remove(UUID id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public LinkedUserDetailsDTO updateFully(UUID id, @Valid LinkedUserDetailsDTO dto) {
        // Dacă dto e invalid, Spring aruncă automat ConstraintViolationException
        LinkedUser entity = null;
        try {
            entity = userRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        } catch (ChangeSetPersister.NotFoundException e) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new RuntimeException(e);
        }

        entity.setEmail(dto.getEmail());

        return LinkedUserBuilder.toUserDetailsDTO(userRepository.save(entity));
    }

}
