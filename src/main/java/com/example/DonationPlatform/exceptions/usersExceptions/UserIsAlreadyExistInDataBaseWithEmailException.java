package com.example.DonationPlatform.exceptions.usersExceptions;

public class UserIsAlreadyExistInDataBaseWithEmailException extends Exception{

    private String email;

    public UserIsAlreadyExistInDataBaseWithEmailException(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User is already exist with login "+email;
    }
}