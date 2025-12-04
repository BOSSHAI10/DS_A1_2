package com.example.users.repositories;

import com.example.users.entities.User;
import com.example.users.entities.roles.Role;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Example: JPA generate query by existing field
     */
    List<User> findByName(String name);

    /**
     * Example: Custom query
     */
    @Query(value = "SELECT p " +
            "FROM users p " +
            "WHERE p.name = :name " +
            "AND p.age >= 60  ")
    Optional<List<User>> findSeniorsByName(@Param("name") String name);

    @Query(value = "SELECT p " +
            "FROM users p " +
            "WHERE p.email = :email ")
    Optional<User> findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    boolean existsById(UUID user_id);

    @Modifying
    @Query(value = "UPDATE users u " +
                    "SET u.name =: name, u.email =: email, u.age =: age, u.role =: role " +
                    "WHERE u.id =: user_id ")
    User updateFully (@Param("user_id") UUID user_id, @Param("name") String name,
                      @Email @Param("email") String email,  @Param("age") Integer age,
                      @Param("role") Role role);

}
