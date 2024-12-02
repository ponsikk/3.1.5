package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.security.core.GrantedAuthority;

public enum  Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_GUEST;


    @Override
    public String getAuthority() {
        return name();
    }

    @JsonCreator
    public static Role fromString(String role) {
        switch (role.toUpperCase()) {
            case "USER":
                return ROLE_USER;
            case "ADMIN":
                return ROLE_ADMIN;
            case "GUEST":
                return ROLE_GUEST;
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}

