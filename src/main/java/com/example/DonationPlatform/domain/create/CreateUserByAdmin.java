package com.example.DonationPlatform.domain.create;

import com.example.DonationPlatform.annotation.Email;
import com.example.DonationPlatform.annotation.Login;
import com.example.DonationPlatform.annotation.Password;
import lombok.Data;

import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Class designed to create user account for admin by admin opportunity: setting special field, for example role, deleteAccount, ratingOfUsers, currentAmountOnAccount, totalAmountOnAccount.
 **/
@Data
public class CreateUserByAdmin {
    @Email
    private String email;

    @Password
    private String password;

    @Size(min = 3, max = 30)
    private String nickName;

    @Login
    private String login;
    private Date birthdate;
    private Date dateOfCreateAccount;
    private int totalAmountOfTransfers;
    private int currentAmountOnAccount;
    private String ratingOfUsers;
    private boolean deleteOfAccount;
    private String role;
}