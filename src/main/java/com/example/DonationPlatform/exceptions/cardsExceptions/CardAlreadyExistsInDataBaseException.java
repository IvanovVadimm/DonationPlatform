package com.example.DonationPlatform.exceptions.cardsExceptions;

public class CardAlreadyExistsInDataBaseException extends Exception {
    private final String cardNumber;

    public CardAlreadyExistsInDataBaseException(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "This card with number:" + cardNumber + " existed in database!";
    }
}
