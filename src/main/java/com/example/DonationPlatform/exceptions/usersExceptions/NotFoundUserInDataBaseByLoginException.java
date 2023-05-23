package com.example.DonationPlatform.exceptions.usersExceptions;

public class NotFoundUserInDataBaseByLoginException extends Exception {
    private final String login;

    public NotFoundUserInDataBaseByLoginException(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "User with login " + login + " not exist in database";
    }
}