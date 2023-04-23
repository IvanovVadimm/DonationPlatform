package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface ICardRepository extends JpaRepository<Card, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM cards_table ct INNER JOIN l_cards_of_users lct on ct.id = lct.card_id WHERE user_id =:userId")
    ArrayList<Card> findAllCardByUserId(Integer userId);

    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT * FROM cards_table c WHERE c.is_deleted = true and c.id = :id)")
    boolean isDeletedCardInDataBaseByIdCardsChecked(int id);

    boolean findByNumberOfCard(String numberOfCard);

    boolean existsCardByNumberOfCard(String numberOfCard);

    @Modifying
    @Transactional //or will generatedTransactionRequiredException, when we want modifying database without open transaction
    @Query(nativeQuery = true, value = "UPDATE cards_table c SET is_deleted = true WHERE c.card_number = :numberOfCard", countQuery = "SELECT * FROM cards_table")
    void deleteCardByNumberOfCard(String numberOfCard);
}
