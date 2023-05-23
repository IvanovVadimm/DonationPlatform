package com.example.DonationPlatform.exceptions.cardsExceptions;

public class CardAlreadyExistsInDataBaseWithCvvException extends Exception {
    @Override
    public String toString() {
        return "Card already exist in data base with that cvv";
    }
}
