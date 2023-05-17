package com.example.DonationPlatform.domain.response;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;

/** Class for getting information about transaction for user**/
@Component
@Data
public class TransactionAboutUserById {

    private int id;

    private double amountOfTransfer;

    private Date dateOfTransaction;

    private String receiverNickname;
}