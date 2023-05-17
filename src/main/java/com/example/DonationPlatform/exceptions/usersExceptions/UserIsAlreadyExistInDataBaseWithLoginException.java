package com.example.DonationPlatform.exceptions.usersExceptions;

public class UserIsAlreadyExistInDataBaseWithLoginException extends Exception{

    private String login;

    public UserIsAlreadyExistInDataBaseWithLoginException(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "User is already exist with login "+login;
    }
}