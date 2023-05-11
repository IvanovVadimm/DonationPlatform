package com.example.DonationPlatform.domain.DAOTransaction;

import com.example.DonationPlatform.domain.DAOUser.DAOUserWithAllInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Date;

/**Класс нуже чтобы вытащить всю всю информацию о транзакциях, используют только админы**/

@Data
@Component
@Entity
@ToString(exclude = {"sender","receiver"})
@EqualsAndHashCode(exclude = {"sender","receiver"})
@Table(name = "transaction_table")
public class DAOTransactionWithAllInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_seq_gen")
    @SequenceGenerator(name = "transaction_id_seq_gen", sequenceName = "transaction_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "amount_of_transfer")
    private double amountOfTransfer;

    @Column(name = "date_of_transaction")
    private Date dateOfTransaction;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private DAOUserWithAllInfo sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private DAOUserWithAllInfo receiver;
}
