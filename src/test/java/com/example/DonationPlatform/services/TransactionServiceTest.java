package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.create.CreateTransactionByUser;
import com.example.DonationPlatform.domain.daotransaction.DaoTransactionWithLightInformation;
import com.example.DonationPlatform.domain.daouser.DaoUserWithAllInfo;
import com.example.DonationPlatform.exceptions.transactionsExceptions.FailCreateTransactionByDeletedReceiverException;
import com.example.DonationPlatform.exceptions.transactionsExceptions.FailCreateTransactionBySenderDontHaveEnoughSumOnAccount;
import com.example.DonationPlatform.exceptions.transactionsExceptions.TransactionNotFoundByIdExceptions;
import com.example.DonationPlatform.exceptions.usersExceptions.NoRightToPerformActionsException;
import com.example.DonationPlatform.exceptions.usersExceptions.NotFoundUserInDataBaseByIdException;
import com.example.DonationPlatform.repository.IdaoTransactionWithLightInformation;
import com.example.DonationPlatform.repository.IuserRepository;
import com.example.DonationPlatform.utils.ReturnLoginSecurityUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private IuserRepository userRepository;
    @Mock
    private IdaoTransactionWithLightInformation iDaoTransactionWithLightInformation;
    @Mock
    private UserService userService;
    @Mock
    private ReturnLoginSecurityUser returnLoginSecurityUser;
    private final DaoUserWithAllInfo receiverUser = new DaoUserWithAllInfo();
    private final DaoUserWithAllInfo senderUser = new DaoUserWithAllInfo();
    private final CreateTransactionByUser createTransactionByUser = new CreateTransactionByUser();
    private final DaoTransactionWithLightInformation transaction = new DaoTransactionWithLightInformation();

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(userRepository, iDaoTransactionWithLightInformation, userService, returnLoginSecurityUser);
        String securityReceiverUserLogin = "80331112233";
        String securitySenderUserLogin = "80331112233";
        receiverUser.setId(1);
        receiverUser.setEmail("test@mail.ru");
        receiverUser.setLogin(securityReceiverUserLogin);
        receiverUser.setPassword("testPassword");
        receiverUser.setCurrentAmountOnAccount(200);
        receiverUser.setDateOfCreateAccount(new Date(new java.util.Date().getTime()));
        receiverUser.setRatingOfUsers("testRating");
        receiverUser.setTotalAmountOfTransfers(0);
        receiverUser.setRole("USER");
        senderUser.setId(2);
        senderUser.setEmail("test1@mail.ru");
        senderUser.setLogin(securitySenderUserLogin);
        senderUser.setPassword("testPassword1");
        senderUser.setCurrentAmountOnAccount(199);
        senderUser.setDateOfCreateAccount(new Date(new java.util.Date().getTime()));
        senderUser.setRatingOfUsers("testRating");
        senderUser.setTotalAmountOfTransfers(0);
        senderUser.setRole("USER");
        returnLoginSecurityUser = new ReturnLoginSecurityUser();
        transaction.setId(0);
        transaction.setDateOfTransaction(new Date(new java.util.Date().getTime()));
        transaction.setReceiverId(1);
        transaction.setSenderId(2);
        transaction.setAmountOfTransfer(99.0);
        createTransactionByUser.setDateOfTransaction(transaction.getDateOfTransaction());
        createTransactionByUser.setAmountOfTransfer(transaction.getAmountOfTransfer());
        createTransactionByUser.setReceiverId(transaction.getReceiverId());
    }

    @Test
    void createTransaction() throws FailCreateTransactionBySenderDontHaveEnoughSumOnAccount, NotFoundUserInDataBaseByIdException, FailCreateTransactionByDeletedReceiverException, NoRightToPerformActionsException {
        when(userRepository.findByLogin(any())).thenReturn(senderUser);
        when(iDaoTransactionWithLightInformation.countOnAmountIsMoreThanOnOperation(senderUser.getId(), transaction.getAmountOfTransfer())).thenReturn(true);
        when(userRepository.existsById(receiverUser.getId())).thenReturn(true);
        when(userRepository.findById(senderUser.getId())).thenReturn(Optional.of(senderUser));
        when(userService.getUserById(senderUser.getId())).thenReturn(Optional.of(senderUser));
        iDaoTransactionWithLightInformation.save(transaction);
        boolean result = transactionService.createTransaction(createTransactionByUser);
        assertTrue(result);
        verify(iDaoTransactionWithLightInformation).save(transaction);
        verify(iDaoTransactionWithLightInformation).countOnAmountIsMoreThanOnOperation(senderUser.getId(), transaction.getAmountOfTransfer());
        verify(iDaoTransactionWithLightInformation).withdrawalCashFromAccount(transaction.getAmountOfTransfer(), senderUser.getId());
        verify(iDaoTransactionWithLightInformation).gettingMoneyIntoAnAccount(transaction.getAmountOfTransfer(), receiverUser.getId());
    }

    @Test
    void getTransactionWithLightInformationById() throws NoRightToPerformActionsException, TransactionNotFoundByIdExceptions {
        ArrayList<DaoTransactionWithLightInformation> listOfTransaction = new ArrayList<>();
        listOfTransaction.add(transaction);
        when(userRepository.findByLogin(any())).thenReturn(senderUser);
        when(iDaoTransactionWithLightInformation.findAllBySenderId(senderUser.getId())).thenReturn(listOfTransaction);
        when(iDaoTransactionWithLightInformation.findById(transaction.getId())).thenReturn(Optional.of(transaction));
        Optional<DaoTransactionWithLightInformation> result = transactionService.getTransactionWithLightInformationById(transaction.getId());
        assertTrue(result.isPresent());
        verify(iDaoTransactionWithLightInformation).findById(transaction.getId());
    }

    @Test
    void getAllOfTransactionAboutUserByUserIdForUser() throws NotFoundUserInDataBaseByIdException, NoRightToPerformActionsException {
        ArrayList<DaoTransactionWithLightInformation> listOfTransaction = new ArrayList<>();
        listOfTransaction.add(transaction);
        when(userRepository.findByLogin(any())).thenReturn(senderUser);
        when(userRepository.existsById(senderUser.getId())).thenReturn(true);
        when(iDaoTransactionWithLightInformation.findAllBySenderId(senderUser.getId())).thenReturn(listOfTransaction);
        when(userRepository.findById(receiverUser.getId())).thenReturn(Optional.of(senderUser));
        boolean isThereInList = (transactionService.getAllOfTransactionAboutUserByUserIdForUser(senderUser.getId())).isPresent();
        assertTrue(isThereInList);
    }
}