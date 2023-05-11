package com.example.DonationPlatform.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;
/** Класс для вывода информации в виде списка транзакций самому пользователю **/
@Component
@Data
public class TransactionAboutUserById {

    private int id;

    private double amountOfTransfer;

    private Date dateOfTransaction;

    private String receiverNickname;
}
