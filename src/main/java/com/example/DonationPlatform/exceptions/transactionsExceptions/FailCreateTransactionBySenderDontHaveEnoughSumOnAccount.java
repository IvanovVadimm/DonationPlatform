package com.example.DonationPlatform.exceptions.transactionsExceptions;

public class FailCreateTransactionBySenderDontHaveEnoughSumOnAccount extends Exception {

    private int senderId;

    public FailCreateTransactionBySenderDontHaveEnoughSumOnAccount(int senderId) {
        this.senderId = senderId;
    }

    @Override
    public String toString() {
        return "Sender with id: " + senderId + " doesn't have enough money on his account !";
    }
}