package com.example.DonationPlatform.domain.request;

import com.example.DonationPlatform.annotation.Email;
import com.example.DonationPlatform.annotation.Login;
import com.example.DonationPlatform.annotation.Password;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@AllArgsConstructor
public class RegistrationOfUsers {

    public RegistrationOfUsers(){
    }

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