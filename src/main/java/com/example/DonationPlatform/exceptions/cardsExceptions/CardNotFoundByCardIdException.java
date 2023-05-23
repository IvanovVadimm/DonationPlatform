package com.example.DonationPlatform.exceptions.cardsExceptions;

public class CardNotFoundByCardIdException extends Exception {
    private final int id;

    public CardNotFoundByCardIdException(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Card with id " + id + " not exist in database";
    }
}