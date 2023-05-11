package com.example.DonationPlatform.domain.DAOTransaction;

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

/**Класс нужен чтобы создать транзакцию**/

@Data
@Component
@Entity
@Table(name = "transaction_table")
public class DAOTransactionWithLightInformation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_seq_gen")
    @SequenceGenerator(name = "transaction_id_seq_gen", sequenceName = "transaction_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "amount_of_transfer")
    private double amountOfTransfer;

    @Column(name = "date_of_transaction")
    private Date dateOfTransaction;

    @Column(name = "sender_id")
    private int senderId;

    @Column(name = "receiver_id")
    private int receiverId;
}