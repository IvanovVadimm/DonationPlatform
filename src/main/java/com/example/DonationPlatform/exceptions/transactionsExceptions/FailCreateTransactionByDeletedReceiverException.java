package com.example.DonationPlatform.exceptions.transactionsExceptions;

public class FailCreateTransactionByDeletedReceiverException extends Exception{
    @Override
    public String toString() {
        return "Transaction receiver was deleted !";
    }
}
