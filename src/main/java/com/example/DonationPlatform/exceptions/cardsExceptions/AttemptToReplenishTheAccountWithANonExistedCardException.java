package com.example.DonationPlatform.exceptions.cardsExceptions;

public class AttemptToReplenishTheAccountWithANonExistedCardException extends Exception {
    private final int id;

    public AttemptToReplenishTheAccountWithANonExistedCardException(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Trying replenish amount with user id: " + id + "  with not exist card on his account!";
    }
}