package com.example.DonationPlatform.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@AllArgsConstructor
public class RegistrationOfUsers {
    @Email
    private String email;
    private String login;
    private String password;
    private Date birthdate;
    @Size(min = 3, max = 200)
    @Column(name = "nickname")
    private String nickName;
}
