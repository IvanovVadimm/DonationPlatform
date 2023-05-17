package com.example.DonationPlatform.exceptions.transactionsExceptions;

public class CreatedTransactionForSameAccountException extends Exception {

    private int id;

    public CreatedTransactionForSameAccountException(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User with id: "+id+" tried make transaction on same account with id: "+ id;
    }
}