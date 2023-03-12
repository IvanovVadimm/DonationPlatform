package com.example.DonationPlatform.services;

import com.example.DonationPlatform.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean checkCardInDataBase(String numberOfCard){
        return cardRepository.checkCardExistInDataBase(numberOfCard);
    }
}
