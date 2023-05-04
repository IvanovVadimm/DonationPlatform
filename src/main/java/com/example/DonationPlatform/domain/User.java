package com.example.DonationPlatform.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
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
import javax.validation.constraints.Size;
import java.sql.Date;

@Component
@Data
@Entity
@ToString(exclude = {"receiver","sender","transactionReceiver","transactionSender"})
@EqualsAndHashCode(exclude = {"receiver","sender","transactionReceiver","transactionSender"})
@Table(name = "user_table")
public class User {
    @Id // обозначает первичный ключ
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq_gen")
    @SequenceGenerator(name = "user_id_seq_gen", sequenceName = "user_table_id_seq", allocationSize = 1)
    private int id;

    @Email
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
    @Column(name = "current_amount_on_account")
    private int currentAmountOnAccount;
    @Size(min = 3, max = 200)
    @Column(name = "nickname")
    private String nickName;
    @Column(name = "rating_of_user")
    private String ratingOfUsers;
    @Column(name = "deleted_account")
    private boolean deleteOfAccount;
    @Column(name = "role_of_user")
    private String role;

    /*@JsonIgnore
    @JsonBackReference
    @ManyToMany(mappedBy = "userSender", fetch = FetchType.LAZY)
    private List<Transaction> senderTransaction;*/
   /* @JsonIgnore
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Transaction receiverTransaction;

    @JsonIgnore
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Transaction senderTransaction;
*/

    //TODO: ManyToMany
    /*@JsonIgnore
    @JsonBackReference
    @ManyToMany(mappedBy = "receiverTransactions")
    List<Transaction> receiver;

    @JsonIgnore
    @JsonBackReference
    @ManyToMany(mappedBy = "senderTransactions")
    List<Transaction> sender;
*/

    @JsonBackReference
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="receiver_id", nullable=false)
    private Transaction transactionReceiver;

    @JsonBackReference
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="sender_id", nullable=false)
    private Transaction transactionSender;


  /*@JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy = "userSender")
    public Transaction getSenderTransaction() {
        return this.senderTransaction;
    }

    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy = "userReceiver")
    public Transaction getReceiverTransaction() {
        return this.receiverTransaction;
    }*/
}
