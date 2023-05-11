package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.DAOCard.DAOCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface IDAOCardRepository extends JpaRepository<DAOCard, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM cards_table ct INNER JOIN l_cards_of_users lct on ct.id = lct.card_id WHERE lct.user_id =:userId")
    ArrayList<DAOCard> findAllCardByUserId(Integer userId);

    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT * FROM cards_table c WHERE c.is_deleted = true and c.id = :cardId)")
    boolean isDeletedCardInDataBaseByIdCardsChecked(Integer cardId);

    boolean findByNumberOfCard(String numberOfCard);

    boolean existsCardByNumberOfCard(String numberOfCard);

    @Modifying
    @Transactional //or will generatedTransactionRequiredException, when we want modifying database without open transaction
    @Query(nativeQuery = true, value = "UPDATE cards_table c SET c.is_deleted = true WHERE c.card_number = :numberOfCard", countQuery = "SELECT * FROM cards_table")
    void deleteCardByNumberOfCard(String numberOfCard);
}
