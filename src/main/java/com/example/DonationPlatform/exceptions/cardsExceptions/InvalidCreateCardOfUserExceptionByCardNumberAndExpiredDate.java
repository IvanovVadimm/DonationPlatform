package com.example.DonationPlatform.exceptions.cardsExceptions;

public class InvalidCreateCardOfUserExceptionByCardNumberAndExpiredDate extends Exception {
    @Override
    public String toString() {
        return "Card data was not validated! Check ones and enter again...";
    }
}

