package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.Card;
import com.example.DonationPlatform.repository.ICardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CardService {
    private ICardRepository cardRepository;

    @Autowired
    public CardService(ICardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public ArrayList<Card> getCardsOfUserByIdOfUser(int id) {
        return cardRepository.findAllCardByUserId(id);
    }

    public Card getCardById(int id) {
        return cardRepository.findById(id).get();
    }

    public boolean checkCardInDataBase(String numberOfCard) {
        return cardRepository.findByNumberOfCard(numberOfCard);
    }

    public boolean creatCardInDatabase(Card card) {
        if (!cardRepository.existsCardByNumberOfCard(card.getNumberOfCard())) {
            Optional<Card> cardOptional = Optional.ofNullable(cardRepository.save(card));
            return cardOptional.isPresent();
        } else {
            return false;
        }
    }
}
