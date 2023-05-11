package com.example.DonationPlatform.domain.DAOUser;

/*
@Data
@Component
@Table(name = "user_table")
@Entity
public class DAOUserWithInfoForOtherUsers {

    @JsonIgnore
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq_gen")
    @SequenceGenerator(name = "user_id_seq_gen", sequenceName = "user_table_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "nickname")
    private String nickname;

    @OneToMany
    @JsonIgnore
    private Set<DAOTransactionWithInfoOnlyForUser> daoUserWithInfoForOtherUsersSenderItems ;

    @JsonIgnore
    @OneToMany
    private Set<DAOTransactionWithInfoOnlyForUser> daoUserWithInfoForOtherUsersReceiverItems ;
}*/
