package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface ICardRepository extends JpaRepository<Card, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM cards_table ct INNER JOIN l_cards_of_users lct on ct.id = lct.card_id WHERE user_id =:userId")
    ArrayList<Card> findAllCardByUserId(Integer userId);

    boolean findByNumberOfCard(String numberOfCard);

    boolean existsCardByNumberOfCard(String numberOfCard);

}
