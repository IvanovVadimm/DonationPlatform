package com.example.DonationPlatform.domain.DAOUser;

import com.example.DonationPlatform.domain.DAOTransaction.DAOTransactionWithAllInfo;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;

@Component
@Data
@Entity
@ToString(exclude = {"senderItems","receiverItems"})
@EqualsAndHashCode(exclude = {"senderItems","receiverItems"})
@Table(name = "user_table")
public class DAOUserWithAllInfo {
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

    @JsonIgnore
    @OneToMany(mappedBy="sender")
    private Set<DAOTransactionWithAllInfo> senderItems;

    @JsonIgnore
    @OneToMany(mappedBy="receiver")
    private Set<DAOTransactionWithAllInfo> receiverItems;

    /*@JsonIgnore
    @OneToMany(mappedBy="senderNickname")
    private Set<DAOTransactionWithInfoOnlyForUser> senderItemsForUserInfo;

    @JsonIgnore
    @OneToMany(mappedBy="receiverNickname")
    private Set<DAOTransactionWithInfoOnlyForUser> receiverItemsForUserInfo;*/
}
