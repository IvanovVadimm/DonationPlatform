package com.example.DonationPlatform.domain.daocard;

import com.example.DonationPlatform.annotation.CvvForVisa;
import com.example.DonationPlatform.annotation.ExpireDateVisaCard;
import com.example.DonationPlatform.annotation.VisaCardNumber;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Date;


@Data
@Component
@Entity
@Table(name = "cards_table")
public class DaoCard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cards_table_id_seq")
    @SequenceGenerator(name = "cards_table_id_seq", sequenceName = "cards_table_id_seq", allocationSize = 1)
    private int id;

    @VisaCardNumber
    @Column(name = "card_number")
    private String numberOfCard;

    @ExpireDateVisaCard
    @Column(name = "expire_date")
    private Date expireDate;

    @CvvForVisa
    @Column(name = "cvv")
    private String cvv;
}