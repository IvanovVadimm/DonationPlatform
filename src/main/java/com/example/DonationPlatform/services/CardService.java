package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.create.CardForUserView;
import com.example.DonationPlatform.domain.daocard.DaoCard;
import com.example.DonationPlatform.domain.daouser.DaoUserWithAllInfo;
import com.example.DonationPlatform.domain.enums.Roles;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardAlreadyExistsInDataBaseException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardAlreadyExistsInDataBaseWithCvvException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardExpiredException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardNotFoundByCardIdException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardNotFoundExceptionByCardNumberException;
import com.example.DonationPlatform.exceptions.cardsExceptions.NotEnteredCardNumberException;
import com.example.DonationPlatform.exceptions.usersExceptions.NoRightToPerformActionsException;
import com.example.DonationPlatform.exceptions.usersExceptions.NotFoundUserInDataBaseByIdException;
import com.example.DonationPlatform.repository.IdaoCardRepository;
import com.example.DonationPlatform.repository.IuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CardService {

    final String adminRole = String.valueOf(Roles.ADMIN);
    private final IdaoCardRepository cardRepository;
    private final IuserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CardService(IdaoCardRepository cardRepository, IuserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean deleteCardOfUserByCardNumber(CardForUserView card) throws CardNotFoundExceptionByCardNumberException, NotEnteredCardNumberException, NoRightToPerformActionsException {

        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        int userId = userRepository.findByLogin(userLogin).get().getId();

        boolean existsCardByNumberOfCard = cardRepository.existsCardByNumberOfCard(card.getNumberOfCard());

        Optional<String> cardNumberOptional = Optional.ofNullable(card.getNumberOfCard());
        String cardNumber = cardNumberOptional.orElseThrow(() -> new NotEnteredCardNumberException());

        if (existsCardByNumberOfCard) {
            if (userRepository.cardOwnershipCheck(userId, cardNumber)) {
                cardRepository.deleteCardByNumberOfCard(card.getNumberOfCard());
                return true;
            } else {
                throw new NoRightToPerformActionsException();
            }
        } else {
            throw new CardNotFoundExceptionByCardNumberException(card.getNumberOfCard());
        }
    }


    public Optional<DaoCard> getCardById(int id) throws CardNotFoundByCardIdException, NotFoundUserInDataBaseByIdException, NoRightToPerformActionsException {

        if (!cardRepository.existsById(id)) {
            throw new CardNotFoundByCardIdException(id);
        }

        String loginSecurityUser = SecurityContextHolder.getContext().getAuthentication().getName();
        DaoUserWithAllInfo securityUser = userRepository.findByLogin(loginSecurityUser).orElseThrow(() -> new NotFoundUserInDataBaseByIdException(id));
        int idSecurityUser = securityUser.getId();
        String roleSecurityUser = securityUser.getRole();

        ArrayList<DaoCard> listOfUserCards = cardRepository.findAllCardByUserId(idSecurityUser);
        boolean searchResult = false;

        for (DaoCard cards : listOfUserCards) {
            if (cards.getId() == id) {
                searchResult = true;
                break;
            }
        }

        Optional<DaoCard> cardOptional;

        if (searchResult || roleSecurityUser.equals(adminRole)) {
            cardOptional = cardRepository.findById(id);
            if (cardOptional.isPresent()) {
                if (!cardRepository.isDeletedCardInDataBaseByIdCardsChecked(id)) {
                    return cardOptional;
                }
            } else {
                throw new CardNotFoundByCardIdException(id);
            }
        } else {
            throw new NoRightToPerformActionsException();
        }
        return cardOptional;
    }

    public boolean checkCardInDataBase(String numberOfCard) {
        return cardRepository.existsCardByNumberOfCard(numberOfCard);
    }

    public boolean createCardInDatabase(DaoCard card) throws CardAlreadyExistsInDataBaseException, CardAlreadyExistsInDataBaseWithCvvException {

        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        int userId = userRepository.findByLogin(userLogin).get().getId();

        Date cardExpiredDate = card.getExpireDate();
        String cardNumber = card.getNumberOfCard();
        String cardCvv = card.getCvv();

        boolean existsByCvv = cardRepository.existsByCvv(cardCvv);

        if (!checkCardInDataBase(cardNumber)) {
            if (!existsByCvv) {
                DaoCard cardForDataBase = new DaoCard();
                cardForDataBase.setNumberOfCard(cardNumber);
                cardForDataBase.setExpireDate(cardExpiredDate);
                cardForDataBase.setCvv(cardCvv);
                cardRepository.save(cardForDataBase);
                int cardId = cardForDataBase.getId();
                cardRepository.putCardAndHisOwnerInDataBase(userId, cardId);
                return true;
            } else {
                throw new CardAlreadyExistsInDataBaseWithCvvException();
            }
        } else {
            throw new CardAlreadyExistsInDataBaseException(card.getNumberOfCard());
        }
    }


    public boolean cardIsExpired(CardForUserView card) throws CardExpiredException {
        Date expireDateOfCard = card.getExpireDate();
        String cardNumber = card.getNumberOfCard();
        LocalDate localDate = LocalDate.now();
        Date currentDate = Date.valueOf(localDate);

        boolean cardIsExpired = currentDate.after(expireDateOfCard);
        if (cardIsExpired) {
            throw new CardExpiredException(cardNumber);
        }
        return false;
    }
}