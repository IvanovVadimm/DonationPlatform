package com.example.DonationPlatform.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Negative;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.sql.Date;

@Component
@Data
@Entity
@ToString(exclude = {"senderTransaction","receiverTransaction"})
@EqualsAndHashCode(exclude = {"senderTransaction","receiverTransaction"})
@Table(name = "user_table")
public class User {
    @Id // обозначает первичный ключ
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq_gen")
    @SequenceGenerator(name = "user_id_seq_gen", sequenceName = "user_table_id_seq", allocationSize = 1)
    private int id;
    //@Email
    @Column(name = "email")
    private String email;
    @Column(name = "login_of_user")
    private String login;
    @Column(name = "password_of_user")
    private String password;
    @Column(name = "birthdate")
    private Date birthdate;
    @Column(name = "date_of_create_account")
    private Date dateOfCreateAccount;
    @Column(name = "total_amount_of_transfers")
    private int totalAmountOfTransfers;
    //@Size(min = 5, max = 20)
    @Column(name = "nickname")
    private String nickName;
    @Column(name = "rating_of_user")
    private String ratingOfUsers;
    @Column(name = "deleted_account")
    private boolean deleteOfAccount;
    @JsonBackReference
    @OneToOne(mappedBy = "userSenderId")
    private Transaction senderTransaction;
    @JsonBackReference
    @OneToOne(mappedBy = "userReceiverId")
    private Transaction receiverTransaction;
}
