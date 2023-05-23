package com.example.DonationPlatform.exceptions.cardsExceptions;

public class CardWasDeletedException extends Exception {
    private final String cardNumber;

    public CardWasDeletedException(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "Card with number" + cardNumber + " was deleted";
    }
}