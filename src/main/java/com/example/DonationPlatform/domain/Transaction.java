package com.example.DonationPlatform.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Transaction {
    private int id;
    private int amountOfTransfer;
    private Date dateOfTransaction;

    private int senderId;

    private int receiverId;
}
