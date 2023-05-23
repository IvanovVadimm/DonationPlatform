package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.create.CardForUserView;
import com.example.DonationPlatform.domain.create.CreateUserByAdmin;
import com.example.DonationPlatform.domain.daocard.DaoCard;
import com.example.DonationPlatform.domain.daotransaction.DaoTransactionWithAllInfo;
import com.example.DonationPlatform.domain.daouser.DaoUserWithAllInfo;
import com.example.DonationPlatform.domain.request.RegistrationOfUsers;
import com.example.DonationPlatform.domain.update.UpdateUserByAdmin;
import com.example.DonationPlatform.domain.update.UpdateUserByUser;
import com.example.DonationPlatform.exceptions.cardsExceptions.AttemptToReplenishTheAccountWithANonExistedCardException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardExpiredException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardNotFoundExceptionByCardNumberException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardWasDeletedException;
import com.example.DonationPlatform.exceptions.usersExceptions.NoRightToPerformActionsException;
import com.example.DonationPlatform.exceptions.usersExceptions.NotFoundUserInDataBaseByIdException;
import com.example.DonationPlatform.exceptions.usersExceptions.UserIsAlreadyExistInDataBaseWithEmailException;
import com.example.DonationPlatform.exceptions.usersExceptions.UserIsAlreadyExistInDataBaseWithLoginException;
import com.example.DonationPlatform.exceptions.usersExceptions.UserIsAlreadyExistInDataBaseWithNickNameException;
import com.example.DonationPlatform.repository.IdaoCardRepository;
import com.example.DonationPlatform.repository.IdaoTransactionWithAllInformation;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private IuserRepository userRepository;
    @Mock
    private IdaoCardRepository cardRepository;
    @Mock
    private CardService cardService;
    @Mock
    private IdaoTransactionWithAllInformation iDaoTransactionRepositoryWithAllInformation;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ReturnLoginSecurityUser returnLoginSecurityUser;
    DaoUserWithAllInfo user = new DaoUserWithAllInfo();
    UpdateUserByAdmin updateUserByAdmin = new UpdateUserByAdmin();
    UpdateUserByUser updateUserByUser = new UpdateUserByUser();
    CreateUserByAdmin createUserByAdmin = new CreateUserByAdmin();
    CardForUserView cardForUserView = new CardForUserView();
    DaoTransactionWithAllInfo daoTransactionWithAllInfo = new DaoTransactionWithAllInfo();
    DaoCard daoCard = new DaoCard();
    RegistrationOfUsers registrationOfUsers;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, cardRepository, cardService, iDaoTransactionRepositoryWithAllInformation, passwordEncoder, returnLoginSecurityUser);
        user.setId(0);
        user.setEmail("test@mail.ru");
        user.setLogin("80291112233");
        user.setPassword("testPassword");
        user.setBirthdate(new Date(new java.util.Date().getTime()));
        user.setNickName("testNickname");
        user.setDeleteOfAccount(false);
        user.setCurrentAmountOnAccount(0);
        user.setDateOfCreateAccount(new Date(new java.util.Date().getTime()));
        user.setRatingOfUsers("firstLevel");
        user.setTotalAmountOfTransfers(0);
        user.setRole("USER");
        updateUserByAdmin.setId(user.getId());
        updateUserByAdmin.setEmail(user.getEmail());
        updateUserByAdmin.setLogin(user.getLogin());
        updateUserByAdmin.setPassword(user.getPassword());
        updateUserByAdmin.setRatingOfUsers(user.getRatingOfUsers());
        updateUserByAdmin.setRole(user.getRole());
        updateUserByUser.setId(user.getId());
        updateUserByUser.setEmail(user.getEmail());
        updateUserByUser.setLogin(user.getLogin());
        updateUserByUser.setPassword(user.getPassword());
        updateUserByUser.setBirthdate(new Date(new java.util.Date().getTime()));
        createUserByAdmin.setEmail(user.getEmail());
        createUserByAdmin.setPassword(user.getPassword());
        createUserByAdmin.setNickName(user.getNickName());
        createUserByAdmin.setLogin(user.getLogin());
        createUserByAdmin.setBirthdate(user.getBirthdate());
        createUserByAdmin.setDateOfCreateAccount(user.getDateOfCreateAccount());
        createUserByAdmin.setTotalAmountOfTransfers(user.getTotalAmountOfTransfers());
        createUserByAdmin.setCurrentAmountOnAccount(user.getCurrentAmountOnAccount());
        createUserByAdmin.setRatingOfUsers(user.getRatingOfUsers());
        createUserByAdmin.setDeleteOfAccount(user.getDeleteOfAccount());
        createUserByAdmin.setRole(user.getRole());
        registrationOfUsers = new RegistrationOfUsers(user.getEmail(), user.getLogin(), user.getPassword(), user.getNickName(), user.getBirthdate());
        cardForUserView.setNumberOfCard("4111222233334444");
        cardForUserView.setExpireDate(new Date(new java.util.Date().getTime()));
        daoCard.setCvv(passwordEncoder.encode("221"));
        daoCard.setNumberOfCard(cardForUserView.getNumberOfCard());
        daoCard.setId(1);
        daoCard.setExpireDate(cardForUserView.getExpireDate());
    }

    @Test
    void getUserById() throws NotFoundUserInDataBaseByIdException, NoRightToPerformActionsException {
        when(userRepository.findByLogin(any())).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Optional<DaoUserWithAllInfo> result = userService.getUserById(user.getId());
        assertTrue(result.isPresent());
        verify(userRepository).findById(user.getId());
    }

    @Test
    void getAllUser() {
        List<DaoUserWithAllInfo> listOfUsers = new ArrayList<>();
        listOfUsers.add(user);
        when(userRepository.findAll()).thenReturn(listOfUsers);
        Optional<List<DaoUserWithAllInfo>> result = userService.getAllUser();
        assertEquals(result.get(), listOfUsers);
        verify(userRepository).findAll();
    }

    @Test
    void updateUserByAdmin() throws NotFoundUserInDataBaseByIdException, UserIsAlreadyExistInDataBaseWithNickNameException, UserIsAlreadyExistInDataBaseWithLoginException, UserIsAlreadyExistInDataBaseWithEmailException {
        when(userRepository.findById(updateUserByAdmin.getId())).thenReturn(Optional.ofNullable(user));
        when(userRepository.existsUserByEmail(updateUserByAdmin.getEmail())).thenReturn(false);
        when(userRepository.existsUserByNickName(updateUserByAdmin.getNickName())).thenReturn(false);
        when(userRepository.existsUserByLogin(updateUserByAdmin.getLogin())).thenReturn(false);
        boolean result = userService.updateUserByAdmin(updateUserByAdmin);
        assertTrue(result);
        verify(userRepository).existsUserByLogin(user.getLogin());
    }

    @Test
    void updateUserByUser() throws UserIsAlreadyExistInDataBaseWithLoginException, UserIsAlreadyExistInDataBaseWithNickNameException, NotFoundUserInDataBaseByIdException, NoRightToPerformActionsException, UserIsAlreadyExistInDataBaseWithEmailException {
        when(userRepository.findByLogin(any())).thenReturn(user);
        when(userRepository.findById(updateUserByUser.getId())).thenReturn(Optional.ofNullable(user));
        when(userRepository.existsUserByEmail(updateUserByUser.getEmail())).thenReturn(false);
        when(userRepository.existsUserByNickName(updateUserByUser.getNickName())).thenReturn(false);
        when(userRepository.existsUserByLogin(updateUserByUser.getLogin())).thenReturn(false);
        boolean result = userService.updateUserByUser(updateUserByUser);
        assertTrue(result);
        verify(userRepository).existsUserByLogin(user.getLogin());
    }

    @Test
    void createUser() throws UserIsAlreadyExistInDataBaseWithNickNameException, UserIsAlreadyExistInDataBaseWithLoginException, UserIsAlreadyExistInDataBaseWithEmailException {
        when(userRepository.existsUserByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsUserByNickName(user.getNickName())).thenReturn(false);
        when(userRepository.existsUserByLogin(user.getLogin())).thenReturn(false);
        userService.createUser(createUserByAdmin);
        verify(userRepository).existsUserByLogin(user.getLogin());
    }

    @Test
    void userIsDeleted() throws NoRightToPerformActionsException {
        when(userRepository.findByLogin(any())).thenReturn(user);
        boolean result = userService.deleteUser(user.getId());
        assertTrue(result);
        verify(userRepository).deleteById(user.getId());
    }

    @Test
    void userRegistration() throws UserIsAlreadyExistInDataBaseWithLoginException, UserIsAlreadyExistInDataBaseWithNickNameException, UserIsAlreadyExistInDataBaseWithEmailException {
        when(userRepository.existsUserByLogin(registrationOfUsers.getLogin())).thenReturn(false);
        when(userRepository.existsUserByEmail(registrationOfUsers.getEmail())).thenReturn(false);
        when(userRepository.existsUserByNickName(registrationOfUsers.getNickName())).thenReturn(false);
        when(passwordEncoder.encode(registrationOfUsers.getPassword())).thenReturn(user.getPassword());
        userService.userRegistration(registrationOfUsers);
        verify(userRepository).existsUserByNickName(registrationOfUsers.getNickName());
    }

    @Test
    void deleteUser() throws NoRightToPerformActionsException {
        when(userRepository.findByLogin(any())).thenReturn(user);
        boolean result = userService.deleteUser(user.getId());
        assertTrue(result);
        verify(userRepository).deleteById(user.getId());
    }

    @Test
    void putMoneyOnCurrentAmount() throws CardExpiredException, AttemptToReplenishTheAccountWithANonExistedCardException, CardWasDeletedException, CardNotFoundExceptionByCardNumberException {
        when(userRepository.findByLogin(any())).thenReturn(user);
        when(userRepository.cardOwnershipCheck(user.getId(), cardForUserView.getNumberOfCard())).thenReturn(true);
        when(cardRepository.isDeletedCardInDataBaseByNumberCardCardsChecked(cardForUserView.getNumberOfCard())).thenReturn(false);
        when(cardRepository.existsCardByNumberOfCard(cardForUserView.getNumberOfCard())).thenReturn(true);
        when(cardService.cardIsExpired(cardForUserView)).thenReturn(false);
        boolean result = userService.putMoneyOnCurrentAmount(1000, cardForUserView);
        assertTrue(result);
        verify(userRepository).putMoneyOnCurrentAmount(1000, user.getId());
    }

    @Test
    void getCardsOfUserByIdOfUser() throws NoRightToPerformActionsException {
        ArrayList<DaoCard> cardsList = new ArrayList<>();
        cardsList.add(daoCard);
        when(userRepository.findByLogin(null)).thenReturn(user);
        when(cardRepository.findAllCardByUserId(user.getId())).thenReturn(cardsList);
        ArrayList<DaoCard> resultList = userService.getCardsOfUserByIdOfUser(user.getId());
        assertEquals(resultList, cardsList);
    }

    @Test
    void getAllOfTransactionAboutUserByIdForAdmin() throws NotFoundUserInDataBaseByIdException {
        ArrayList<DaoTransactionWithAllInfo> transactionList = new ArrayList<>();
        transactionList.add(daoTransactionWithAllInfo);
        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(iDaoTransactionRepositoryWithAllInformation.findAllBySenderId(user.getId())).thenReturn(transactionList);
        ArrayList<DaoTransactionWithAllInfo> resultList = userService.getAllOfTransactionAboutUserByIdForAdmin(user.getId()).get();
        assertEquals(transactionList, resultList);
    }
}