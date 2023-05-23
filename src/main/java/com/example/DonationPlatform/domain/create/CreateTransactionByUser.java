package com.example.DonationPlatform.domain.create;

import lombok.Data;

import java.sql.Date;

@Data
public class CreateTransactionByUser {
    private double amountOfTransfer;
    private Date dateOfTransaction;
    private int receiverId;
}