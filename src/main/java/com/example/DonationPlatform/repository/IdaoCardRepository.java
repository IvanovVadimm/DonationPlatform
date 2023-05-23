package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.daocard.DaoCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface IdaoCardRepository extends JpaRepository<DaoCard, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM cards_table ct INNER JOIN l_cards_of_users lct on ct.id = lct.card_id WHERE lct.user_id =:userId")
    ArrayList<DaoCard> findAllCardByUserId(Integer userId);

    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT * FROM cards_table c WHERE c.is_deleted = true and c.id = :cardId)")
    boolean isDeletedCardInDataBaseByIdCardsChecked(Integer cardId);

    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT * FROM cards_table c WHERE c.is_deleted = true and c.card_number = :cardNumber)")
    boolean isDeletedCardInDataBaseByNumberCardCardsChecked(String cardNumber);

    boolean existsCardByNumberOfCard(String numberOfCard);

    boolean existsByCvv(String cvv);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO l_cards_of_users (card_id, user_id) VALUES (:cardId,:userId)", countQuery = "SELECT * FROM cards_table")
    void putCardAndHisOwnerInDataBase(Integer userId, Integer cardId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE cards_table SET is_deleted = true WHERE card_number = :numberOfCard", countQuery = "SELECT * FROM cards_table")
    void deleteCardByNumberOfCard(String numberOfCard);
}