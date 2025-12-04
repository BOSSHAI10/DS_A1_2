package com.example.devices.repositories;

import com.example.devices.entities.LinkedUser;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LinkedUserRepository extends JpaRepository<LinkedUser, UUID> {

    /**
     * Example: JPA generate query by existing field
     */


    /**
     * Example: Custom query
     */


    @Query(value = "SELECT p " +
            "FROM linked_users p " +
            "WHERE p.email = :email ")
    Optional<LinkedUser> findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    boolean existsById(UUID user_id);

}
