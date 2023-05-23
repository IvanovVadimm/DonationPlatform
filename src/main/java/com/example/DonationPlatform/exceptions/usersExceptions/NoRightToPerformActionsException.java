package com.example.DonationPlatform.exceptions.usersExceptions;

public class NoRightToPerformActionsException extends Exception {
    @Override
    public String toString() {
        return "User doesn't have rights to perform action!";
    }
}