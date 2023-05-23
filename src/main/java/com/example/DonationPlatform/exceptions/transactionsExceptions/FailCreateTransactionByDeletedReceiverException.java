package com.example.DonationPlatform.exceptions.transactionsExceptions;

public class FailCreateTransactionByDeletedReceiverException extends Exception {
    private final int receiverId;

    public FailCreateTransactionByDeletedReceiverException(int receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public String toString() {
        return "Transaction receiver with id " + receiverId + " was deleted !";
    }
}