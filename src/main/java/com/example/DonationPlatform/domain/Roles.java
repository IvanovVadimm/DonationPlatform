package com.example.DonationPlatform.domain;

public enum Roles {
    USER("USER"),
    ADMIN("ADMIN");
    private final String role;
    Roles(String role){
        this.role = role;
    }
}
