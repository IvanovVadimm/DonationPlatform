package com.example.DonationPlatform.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Card {
    private int id;
    private String cardNumber;

    private Date expireDateOfCard;
}
