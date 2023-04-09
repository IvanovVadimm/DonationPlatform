package com.example.DonationPlatform.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;

@Data
@Component
@Entity
@Table(name = "cards_table")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cards_table_id_seq")
    @SequenceGenerator(name = "cards_table_id_seq", sequenceName = "cards_table_id_seq", allocationSize = 1)
    private int id;
    @Column(name = "card_number")
    private String numberOfCard;
    @Column(name = "expire_date")
    private Date expireDate;
}
