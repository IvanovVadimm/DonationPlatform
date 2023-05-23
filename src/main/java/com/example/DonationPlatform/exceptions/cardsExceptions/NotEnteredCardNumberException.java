package com.example.DonationPlatform.exceptions.cardsExceptions;

public class NotEnteredCardNumberException extends Exception {
    @Override
    public String toString() {
        return "Not entered card number for deleting";
    }
}
