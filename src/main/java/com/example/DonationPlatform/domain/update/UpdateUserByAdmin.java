package com.example.DonationPlatform.domain.update;

import com.example.DonationPlatform.annotation.Email;
import com.example.DonationPlatform.annotation.Login;
import com.example.DonationPlatform.annotation.Password;
import lombok.Data;

import javax.validation.constraints.Size;
import java.sql.Date;

@Data
public class UpdateUserByAdmin {

    int id;

    @Email
    String email;

    @Size(min = 3, max = 30)
    String nickName;

    @Login
    String login;

    @Password
    String password;

    String role;

    Date birthdate;

    String ratingOfUsers;
}