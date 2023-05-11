package com.example.DonationPlatform.exceptions.cardsExceptions;

public class CardExpiredException extends Exception{
    @Override
    public String toString() {
        return "Card expired!";
    }
}
