package com.example.DonationPlatform.exceptions.cardsExceptions;

public class NullPointerInCreatingCardException extends NullPointerException {
    private final int userId;

    public NullPointerInCreatingCardException(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Was entered not all data for creating card by user with user id: " + userId;
    }
}
