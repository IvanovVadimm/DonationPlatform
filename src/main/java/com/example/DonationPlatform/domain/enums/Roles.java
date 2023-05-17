package com.example.DonationPlatform.domain.enums;

public enum Roles {
    USER("USER"),

    ADMIN("ADMIN");

    private final String roleName;

    Roles(String roleName){
        this.roleName = roleName;
    }

    public String getRole(){
        return roleName;
    }
}