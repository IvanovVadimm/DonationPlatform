package com.example.DonationPlatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Set;

@Data
@Component
@Entity
@ToString(exclude = {"receiverTransactions","senderTransactions","senderUsers","receiverUsers"})
@EqualsAndHashCode(exclude = {"receiverTransactions","senderTransactions","senderUsers","receiverUsers"})
@Table(name = "transaction_table")
public class Transaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_seq_gen")
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

    //TODO ManyToMany
   /* @JsonIgnore
    @JsonManagedReference
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "l_transaction_to_users",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "receiver_id"))
    Set<User> receiverTransactions;

    @JsonIgnore
    @JsonManagedReference
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "l_transaction_to_users",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "sender_id"))
    Set<User> senderTransactions;
*/
    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy="transactionReceiver")
    Set<User> receiverUsers;

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy="transactionSender")
    Set<User> senderUsers;


    /*@JsonIgnore
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")// insertable = false, updatable = false
    private User userSender;

    @JsonIgnore
    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<User> userReceiver;*/

    /*@JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id", referencedColumnName = "id", insertable = false, updatable = false)
    public User getUserSender() {
        return this.userSender;
    }

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", insertable = false, updatable = false)
    public User getUserReceiver() {
        return this.userReceiver;
    }
*/
}
