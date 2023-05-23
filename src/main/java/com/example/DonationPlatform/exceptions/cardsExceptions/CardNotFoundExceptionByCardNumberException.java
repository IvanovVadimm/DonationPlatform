package com.example.DonationPlatform.exceptions.cardsExceptions;

public class CardNotFoundExceptionByCardNumberException extends Exception {
    private final String cardNumber;

    public CardNotFoundExceptionByCardNumberException(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "Card with number " + cardNumber + " not exist in database";
    }
}