package com.example.DonationPlatform.domain.create;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Data
@Component
public class CardForUserView {

    private String numberOfCard;

    private Date expireDate;
}