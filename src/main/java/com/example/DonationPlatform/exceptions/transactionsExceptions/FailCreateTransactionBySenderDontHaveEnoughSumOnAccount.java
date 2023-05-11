package com.example.DonationPlatform.exceptions.transactionsExceptions;

public class FailCreateTransactionBySenderDontHaveEnoughSumOnAccount extends Exception{
    @Override
    public String toString() {
        return "Sender doesn't have enough money on his account !";
    }
}
