package com.example.devices.entities;

import com.example.devices.entities.roles.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "linked_users")
@Table(name = "linked_users")
public class LinkedUser {
    @Id
    @Column(name = "link_id", nullable = false)
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID linkId;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "email", nullable = false)
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "role", nullable = false)
    private Role role;

    // FIX THIS: Add the annotation to map the list
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Device> linkedDevices = new ArrayList<>();

    public LinkedUser() {
    }

    public LinkedUser(UUID userId, String email) {
        this.userId = userId;
        this.email = email;
        this.linkedDevices = new ArrayList<>();
    }

    public LinkedUser(UUID linkId, UUID userId, String email) {
        this.linkId = linkId;
        this.userId = userId;
        this.email = email;
        this.linkedDevices = new ArrayList<>();
    }

    public UUID getLinkId() {
        return linkId;
    }

    public void setLinkId(UUID linkId) {
        this.linkId = linkId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Device> getLinkedDevices() {
        return linkedDevices;
    }

    public void setLinkedDevices(List<Device> linkedDevices) {
        this.linkedDevices = linkedDevices;
    }

    public void addDevice(Device device) {
        linkedDevices.add(device);
    }

    public void removeDevice(Device device) {
        linkedDevices.remove(device);
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
