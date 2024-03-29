package com.example.DonationPlatform.domain.create;

import com.example.DonationPlatform.annotation.ExpireDateVisaCard;
import com.example.DonationPlatform.annotation.VisaCardNumber;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * Class designed to get card for user.
 */
@Data
@Component
public class CardForUserView {
    @VisaCardNumber
    private String numberOfCard;

    @ExpireDateVisaCard
    private Date expireDate;
}