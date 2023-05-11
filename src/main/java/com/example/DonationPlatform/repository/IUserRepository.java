package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.DAOUser.DAOUserWithAllInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<DAOUserWithAllInfo, Integer> {
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE user_table u SET u.deleted_account = true WHERE u.id = :id", countQuery = "SELECT * FROM user_table u WHERE u.id = :id")
    void deleteById(Integer id);

    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT * FROM user_table u WHERE u.deleted_account = true and u.id = :id)")
    boolean isDeletedUserInDataBaseByIdUserChecked(Integer id);

    boolean existsUserByLoginOrEmailOrNickName(String login, String email, String nickName);

    boolean existsById(int id);
    boolean existsByNickNameOrLoginOrEmail(String nickName, String login, String email);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE user_table SET current_amount_on_account = current_amount_on_account+:sum WHERE id = :userId", countQuery = "SELECT * FROM user_table")
    void putMoneyOnCurrentAmount(Integer sum, Integer userId);

    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT * FROM l_cards_of_users l inner join cards_table c on l.card_id = c.id WHERE c.card_number = :cardNumber and l.user_id = :userId)")
    boolean cardOwnershipCheck(Integer userId, String cardNumber);
}
