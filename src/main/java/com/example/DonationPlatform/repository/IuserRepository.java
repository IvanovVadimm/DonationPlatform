package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.daouser.DaoUserWithAllInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IuserRepository extends JpaRepository<DaoUserWithAllInfo, Integer> {

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE user_table SET deleted_account = true WHERE id = :id", countQuery = "SELECT * FROM user_table u WHERE u.id = :id")
    void deleteById(Integer id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE user_table SET current_amount_on_account = current_amount_on_account+:sum WHERE id = :userId", countQuery = "SELECT * FROM user_table")
    void putMoneyOnCurrentAmount(Integer sum, Integer userId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE user_table SET rating_of_user = :rating WHERE id = :id", countQuery = "SELECT * FROM user_table u WHERE u.id = :id")
    void setRatingForUserById(Integer id, String rating);

    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT * FROM user_table u WHERE u.deleted_account = true and u.id = :id)")
    boolean isDeletedUserInDataBaseByIdUserChecked(Integer id);

    boolean existsUserByLogin(String login);

    boolean existsUserByEmail(String email);

    boolean existsUserByNickName(String nickName);

    Optional<DaoUserWithAllInfo> findByLogin(String login);

    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT * FROM l_cards_of_users l inner join cards_table c on l.card_id = c.id WHERE c.card_number = :cardNumber and l.user_id = :userId)")
    boolean cardOwnershipCheck(Integer userId, String cardNumber);
}