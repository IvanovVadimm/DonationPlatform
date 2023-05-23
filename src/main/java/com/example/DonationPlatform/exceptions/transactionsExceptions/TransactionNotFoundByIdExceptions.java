package com.example.DonationPlatform.exceptions.transactionsExceptions;

public class TransactionNotFoundByIdExceptions extends Exception {
    private final int id;

    public TransactionNotFoundByIdExceptions(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Transaction with id " + id + " not exist in database";
    }
}