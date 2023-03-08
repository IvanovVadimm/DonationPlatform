package com.example.DonationPlatform.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.Negative;
import java.sql.Date;

@Component
@Data
public class User {
    private int id;
    @Email
    private String email;
    private String login;
    private String password;
    private Date birthdate;
    private Date dateOfCreateAccount;
    @Negative
    private int totalAmountOfTransfers;
    private String nickName;
    private String ratingOfUsers;

    private boolean deleteOfAccount;
}
