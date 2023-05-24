package com.example.DonationPlatform.domain.create;

import com.example.DonationPlatform.annotation.CvvForVisa;
import com.example.DonationPlatform.annotation.ExpireDateVisaCard;
import com.example.DonationPlatform.annotation.VisaCardNumber;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.sql.Date;

/**
 * Class designed to create card for user.
 **/
@Data
@Component
public class CardForEnteringByUserAndCreateInDataBase {
    @VisaCardNumber
    private String numberOfCard;

    @ExpireDateVisaCard
    @Column(name = "expire_date")
    private Date expireDate;

    @CvvForVisa
    @Column(name = "cvv")
    private String cvv;
}
