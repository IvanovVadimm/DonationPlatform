package com.example.DonationPlatform.exceptions.cardsExceptions;

public class InvalidCreateCardOfUserException extends Exception{

    @Override
    public String toString() {
        return "Card creation data not validated";
    }
}
