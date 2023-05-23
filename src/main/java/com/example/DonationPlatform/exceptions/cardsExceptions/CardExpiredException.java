package com.example.DonationPlatform.exceptions.cardsExceptions;

public class CardExpiredException extends Exception {
    private final String cardNumber;

    public CardExpiredException(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "Card " + this.cardNumber + " was expired!";
    }
}