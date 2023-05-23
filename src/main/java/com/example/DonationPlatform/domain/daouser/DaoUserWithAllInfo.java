package com.example.DonationPlatform.domain.daouser;

import com.example.DonationPlatform.annotation.Email;
import com.example.DonationPlatform.annotation.Login;
import com.example.DonationPlatform.annotation.Password;
import com.example.DonationPlatform.domain.daotransaction.DaoTransactionWithAllInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;

@Component
@Data
@Entity
@ToString(exclude = {"senderItems", "receiverItems"})
@EqualsAndHashCode(exclude = {"senderItems", "receiverItems"})
@Table(name = "user_table")
public class DaoUserWithAllInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq_gen")
    @SequenceGenerator(name = "user_id_seq_gen", sequenceName = "user_table_id_seq", allocationSize = 1)
    private int id;

    @Email
    @Column(name = "email")
    private String email;

    @Login
    @Column(name = "login_of_user")
    private String login;

    @Password
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

    @Size(min = 3, max = 30)
    @Column(name = "nickname")
    private String nickName;

    @Column(name = "rating_of_user")
    private String ratingOfUsers;

    @Column(name = "deleted_account")
    private boolean deleteOfAccount;

    @Column(name = "role_of_user")
    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "sender")
    private Set<DaoTransactionWithAllInfo> senderItems;

    @JsonIgnore
    @OneToMany(mappedBy = "receiver")
    private Set<DaoTransactionWithAllInfo> receiverItems;

    public boolean getDeleteOfAccount() {
        return deleteOfAccount;
    }
}