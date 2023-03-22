package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.Card;
import com.example.DonationPlatform.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.util.ArrayList;

@Service
public class CardService {
    CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public ArrayList<String> getCardsOfUserByIdOfUser(int id){
        return cardRepository.getCardByUserId(id);
    }

    public Card getCardById(int id){
        return cardRepository.getCardById(id);
    }
    public boolean checkCardInDataBase(String numberOfCard){
        return cardRepository.checkCardExistInDataBase(numberOfCard);
    }

    public boolean creatCardInDatabase(String numberOfCard, String expireDate){
        return cardRepository.createCard(numberOfCard, expireDate);
    }
}
