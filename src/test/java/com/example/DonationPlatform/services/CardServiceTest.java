package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.create.CardForEnteringByUserAndCreateInDataBase;
import com.example.DonationPlatform.domain.create.CardForUserView;
import com.example.DonationPlatform.domain.daocard.DaoCard;
import com.example.DonationPlatform.domain.daouser.DaoUserWithAllInfo;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardAlreadyExistsInDataBaseException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardAlreadyExistsInDataBaseWithCvvException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardNotFoundByCardIdException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardNotFoundExceptionByCardNumberException;
import com.example.DonationPlatform.exceptions.cardsExceptions.NotEnteredCardNumberException;
import com.example.DonationPlatform.exceptions.usersExceptions.NoRightToPerformActionsException;
import com.example.DonationPlatform.repository.IdaoCardRepository;
import com.example.DonationPlatform.repository.IuserRepository;
import com.example.DonationPlatform.utils.ReturnLoginSecurityUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    @InjectMocks
    private CardService cardService;
    @Mock
    private IdaoCardRepository cardRepository;
    @Mock
    private IuserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ReturnLoginSecurityUser returnLoginSecurityUser;
    private final CardForUserView cardForUserView = new CardForUserView();
    private final DaoCard daoCard = new DaoCard();
    private final DaoUserWithAllInfo user = new DaoUserWithAllInfo();
    private final CardForEnteringByUserAndCreateInDataBase cardForEnteringByUserAndCreateInDataBase = new CardForEnteringByUserAndCreateInDataBase();

    @BeforeEach
    void setUp() {
        cardService = new CardService(cardRepository, userRepository, passwordEncoder, returnLoginSecurityUser);
        String cardNumber = "4111222233334444";
        String securityUserLogin = "80331112233";
        cardForUserView.setNumberOfCard(cardNumber);
        cardForUserView.setExpireDate(new Date(new java.util.Date().getTime()));
        daoCard.setId(1);
        daoCard.setCvv(passwordEncoder.encode("422"));
        daoCard.setNumberOfCard(cardForUserView.getNumberOfCard());
        daoCard.setExpireDate(cardForUserView.getExpireDate());
        user.setId(1);
        user.setEmail("test@mail.ru");
        user.setLogin(securityUserLogin);
        user.setPassword("testPassword");
        user.setCurrentAmountOnAccount(0);
        user.setDateOfCreateAccount(new Date(new java.util.Date().getTime()));
        user.setRatingOfUsers("testRating");
        user.setTotalAmountOfTransfers(0);
        user.setRole("USER");
        returnLoginSecurityUser = new ReturnLoginSecurityUser();
    }

    @Test
    void canDeleteCardOfUserByCardNumber() throws NullPointerException, CardNotFoundExceptionByCardNumberException, NotEnteredCardNumberException, NoRightToPerformActionsException {
        when(userRepository.findByLogin(any())).thenReturn(user);
        when(cardRepository.existsCardByNumberOfCard(cardForUserView.getNumberOfCard())).thenReturn(true);
        when(userRepository.cardOwnershipCheck(user.getId(), cardForUserView.getNumberOfCard())).thenReturn(true);
        boolean result = cardService.deleteCardOfUserByCardNumber(cardForUserView);
        assertTrue(result);
    }

    @Test
    void getCardById() throws CardNotFoundByCardIdException, NoRightToPerformActionsException {
        ArrayList<DaoCard> daoCardsList = new ArrayList<>();
        daoCardsList.add(daoCard);
        when(cardRepository.existsById(daoCard.getId())).thenReturn(true);
        when(userRepository.findByLogin(any())).thenReturn(user);
        when(cardRepository.findAllCardByUserId(user.getId())).thenReturn(daoCardsList);
        when(cardRepository.findById(daoCard.getId())).thenReturn(Optional.of(daoCard));
        when(cardRepository.isDeletedCardInDataBaseByIdCardsChecked(daoCard.getId())).thenReturn(false);
        Optional<DaoCard> result = cardService.getCardById(daoCard.getId());
        assertTrue(result.isPresent());
    }

    @Test
    void checkCardInDataBase() {
        when(cardRepository.existsCardByNumberOfCard(daoCard.getNumberOfCard())).thenReturn(true);
        boolean result = cardRepository.existsCardByNumberOfCard(daoCard.getNumberOfCard());
        assertTrue(result);
        verify(cardRepository, times(1)).existsCardByNumberOfCard(daoCard.getNumberOfCard());
    }

    @Test
    void createCardInDatabase() throws CardAlreadyExistsInDataBaseWithCvvException, CardAlreadyExistsInDataBaseException {
        when(userRepository.findByLogin(any())).thenReturn(user);
        when(cardRepository.existsByCvv(daoCard.getCvv())).thenReturn(false);
        boolean result = cardService.createCardInDatabase(cardForEnteringByUserAndCreateInDataBase);
        assertTrue(result);
        verify(cardRepository, times(1)).existsByCvv(daoCard.getCvv());
    }
}