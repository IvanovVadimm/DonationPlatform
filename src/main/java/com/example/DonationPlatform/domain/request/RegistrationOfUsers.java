package com.example.DonationPlatform.domain.request;

import com.example.DonationPlatform.annotation.Email;
import com.example.DonationPlatform.annotation.Login;
import com.example.DonationPlatform.annotation.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Class allows to make user registration by given needing parameters for that.
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationOfUsers {

    @Email
    private String email;

    @Login
    private String login;

    @Password
    private String password;

    @Size(min = 3, max = 50)
    private String nickName;
    private Date birthdate;
}