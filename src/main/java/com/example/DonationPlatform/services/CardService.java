package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.CardForUsersView;
import com.example.DonationPlatform.domain.DAOCard.DAOCard;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardExpiredException;
import com.example.DonationPlatform.repository.IDAOCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CardService {
    private IDAOCardRepository cardRepository;

    @Autowired
    public CardService(IDAOCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public ArrayList<DAOCard> getCardsOfUserByIdOfUser(int id) {
        return cardRepository.findAllCardByUserId(id);
    }

    public boolean deleteCardOfUserByCardNumber(CardForUsersView card) {
        if (cardRepository.existsCardByNumberOfCard(card.getNumberOfCard())) {
            cardRepository.deleteCardByNumberOfCard(card.getNumberOfCard());
            return true;
        } else {
            return false;
        }
    }

    public Optional<DAOCard> getCardById(int id) {
        Optional<DAOCard> cardOptional = cardRepository.findById(id);
        if (cardOptional.isPresent()) {
            if (!cardRepository.isDeletedCardInDataBaseByIdCardsChecked(id)) {
                return cardOptional; //TODO: возвращает херню
            }
        }
        return cardOptional;
    }

    public boolean checkCardInDataBase(String numberOfCard) {
        return cardRepository.existsCardByNumberOfCard(numberOfCard);
    }

    public boolean creatCardInDatabase(DAOCard card) {
        if (!cardRepository.existsCardByNumberOfCard(card.getNumberOfCard())) {
            Optional<DAOCard> cardOptional = Optional.ofNullable(cardRepository.save(card));
            return cardOptional.isPresent();
        }
            return false;
    }

    public boolean cardIsExpired(CardForUsersView card) throws CardExpiredException {
        Date expireDateOfCard = card.getExpireDate();
        String cardNumber = card.getNumberOfCard();
        LocalDate localDate = LocalDate.now();
        Date currentDate = Date.valueOf(localDate);

        if(currentDate.after(expireDateOfCard)){
            //cardRepository.deleteCardByNumberOfCard(cardNumber);
           // throw new CardExpiredException();
            return true;
        }
        return false;
    }
}