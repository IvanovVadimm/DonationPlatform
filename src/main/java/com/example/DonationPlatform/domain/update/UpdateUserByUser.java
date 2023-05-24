package com.example.DonationPlatform.domain.update;

import com.example.DonationPlatform.annotation.Email;
import com.example.DonationPlatform.annotation.Login;
import com.example.DonationPlatform.annotation.Password;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Class allows to update user data by given needing parameters for that.
 **/
@Component
@Data
public class UpdateUserByUser {
    private int id;

    @Email
    private String email;

    @Size(min = 3, max = 30)
    private String nickName;

    @Login
    private String login;

    @Password
    private String password;

    private Date birthdate;
}