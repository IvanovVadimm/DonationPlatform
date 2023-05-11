package com.example.DonationPlatform.exceptions.usersExceptions;

public class InvalidCreateUserException extends Exception{
    @Override
    public String toString() {
        return "User creation data not validated";
    }
}
