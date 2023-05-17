package com.example.DonationPlatform.exceptions.usersExceptions;

public class NotFoundUserInDataBaseByIdException extends Exception{
    private int id;

    public NotFoundUserInDataBaseByIdException(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User with id " + id + " not exist in database";
    }
}