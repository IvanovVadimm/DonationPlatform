package com.example.DonationPlatform.domain;

import com.example.DonationPlatform.controllers.UserController;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;

@Data
@Component
@Entity
@ToString(exclude = {"userSenderId","userReceiverId"})
@EqualsAndHashCode(exclude = {"userSenderId","userReceiverId"})
@Table(name = "transaction_table")
public class Transaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="transaction_id_seq_gen" )
    @SequenceGenerator(name = "transaction_id_seq_gen", sequenceName = "transaction_id_seq", allocationSize = 1)
    private int id;
    @Column(name = "amount_of_transfer")
    private int amountOfTransfer;
    @Column(name = "date_of_transaction")
    private Date dateOfTransaction;
    @Column(name = "sender_id")
    private int senderId;
    @Column(name = "receiver_id")
    private int receiverId;
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id", referencedColumnName = "id",insertable = false,updatable = false)
    private User userSenderId;
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id",insertable = false,updatable = false)
    private User userReceiverId;
}
