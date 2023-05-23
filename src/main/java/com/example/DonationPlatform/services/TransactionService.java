package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.create.CreateTransactionByUser;
import com.example.DonationPlatform.domain.daotransaction.DaoTransactionWithLightInformation;
import com.example.DonationPlatform.domain.daouser.DaoUserWithAllInfo;
import com.example.DonationPlatform.domain.enums.Rating;
import com.example.DonationPlatform.domain.enums.Roles;
import com.example.DonationPlatform.domain.response.TransactionAboutUserById;
import com.example.DonationPlatform.exceptions.transactionsExceptions.FailCreateTransactionByDeletedReceiverException;
import com.example.DonationPlatform.exceptions.transactionsExceptions.FailCreateTransactionBySenderDontHaveEnoughSumOnAccount;
import com.example.DonationPlatform.exceptions.transactionsExceptions.TransactionNotFoundByIdExceptions;
import com.example.DonationPlatform.exceptions.usersExceptions.NoRightToPerformActionsException;
import com.example.DonationPlatform.exceptions.usersExceptions.NotFoundUserInDataBaseByIdException;
import com.example.DonationPlatform.repository.IdaoTransactionWithLightInformation;
import com.example.DonationPlatform.repository.IuserRepository;
import com.example.DonationPlatform.utils.ReturnLoginSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class TransactionService {
    final String adminRole = String.valueOf(Roles.ADMIN);
    private final IuserRepository userRepository;
    private final IdaoTransactionWithLightInformation iDaoTransactionWithLightInformation;
    private final UserService userService;
    private final ReturnLoginSecurityUser returnLoginSecurityUser;

    @Autowired
    public TransactionService(IuserRepository userRepository, IdaoTransactionWithLightInformation iDaoTransactionWithLightInformation, UserService userService, ReturnLoginSecurityUser returnLoginSecurityUser) {
        this.userRepository = userRepository;
        this.iDaoTransactionWithLightInformation = iDaoTransactionWithLightInformation;
        this.userService = userService;
        this.returnLoginSecurityUser = returnLoginSecurityUser;
    }

    @Transactional
    public boolean createTransaction(CreateTransactionByUser transaction) throws FailCreateTransactionByDeletedReceiverException, FailCreateTransactionBySenderDontHaveEnoughSumOnAccount, NotFoundUserInDataBaseByIdException, NoRightToPerformActionsException {
        String loginSecurityUser = returnLoginSecurityUser.getLoginOfSecurityUser();
        DaoUserWithAllInfo securityUser = userRepository.findByLogin(loginSecurityUser);
        int senderId = securityUser.getId();
        int receiverId = transaction.getReceiverId();
        double amountOfTransfer = transaction.getAmountOfTransfer();
        double totalAmountOfTransfers;
        boolean countOnAmountIsMoreThanOnOperation = iDaoTransactionWithLightInformation.countOnAmountIsMoreThanOnOperation(senderId, amountOfTransfer);
        boolean receiverExist = userRepository.existsById(receiverId);
        if (!receiverExist) {
            throw new FailCreateTransactionByDeletedReceiverException(receiverId);
        }
        if (!countOnAmountIsMoreThanOnOperation) {
            throw new FailCreateTransactionBySenderDontHaveEnoughSumOnAccount(senderId);
        }
        if (countOnAmountIsMoreThanOnOperation && receiverExist) {
            int sumOnCurrentAmountOnAccount = userRepository.findById(senderId).get().getCurrentAmountOnAccount();
            boolean permissionToExecuteTransaction = sumOnCurrentAmountOnAccount - transaction.getAmountOfTransfer() >= 0;
            if (permissionToExecuteTransaction) {
                iDaoTransactionWithLightInformation.withdrawalCashFromAccount(amountOfTransfer, senderId);
                iDaoTransactionWithLightInformation.gettingMoneyIntoAnAccount(amountOfTransfer, receiverId);
                DaoTransactionWithLightInformation daoTransactionWithLightInformation = new DaoTransactionWithLightInformation();
                daoTransactionWithLightInformation.setSenderId(senderId);
                daoTransactionWithLightInformation.setAmountOfTransfer(amountOfTransfer);
                daoTransactionWithLightInformation.setReceiverId(receiverId);
                daoTransactionWithLightInformation.setDateOfTransaction(new Date(new java.util.Date().getTime()));
                iDaoTransactionWithLightInformation.save(daoTransactionWithLightInformation);
                totalAmountOfTransfers = userService.getUserById(senderId).get().getTotalAmountOfTransfers();
                for (Rating rating : Rating.values()) {
                    if (rating.getRequiredAmountOfRatingLevel() <= totalAmountOfTransfers) {
                        userRepository.setRatingForUserById(senderId, rating.getNameOfRatingLevel());
                    }
                }
                return true;
            }
        } else {
            throw new NoRightToPerformActionsException();
        }
        return false;
    }

    public Optional<DaoTransactionWithLightInformation> getTransactionWithLightInformationById(int id) throws TransactionNotFoundByIdExceptions, NoRightToPerformActionsException {
        String loginSecurityUser = returnLoginSecurityUser.getLoginOfSecurityUser();
        DaoUserWithAllInfo securityUser = userRepository.findByLogin(loginSecurityUser);
        int idSecurityUser = securityUser.getId();
        String roleSecurityUser = securityUser.getRole();
        ArrayList<DaoTransactionWithLightInformation> listOfUsersTransactions = iDaoTransactionWithLightInformation.findAllBySenderId(idSecurityUser);
        boolean searchResult = false;
        for (DaoTransactionWithLightInformation transactions : listOfUsersTransactions) {
            if (transactions.getId() == id) {
                searchResult = true;
                break;
            }
        }
        if (searchResult || roleSecurityUser.equals(adminRole)) {
            Optional<DaoTransactionWithLightInformation> transactionOptional = iDaoTransactionWithLightInformation.findById(id);
            if (transactionOptional.isPresent()) {
                return transactionOptional;
            } else {
                throw new TransactionNotFoundByIdExceptions(id);
            }
        } else {
            throw new NoRightToPerformActionsException();
        }
    }

    public Optional<ArrayList<TransactionAboutUserById>> getAllOfTransactionAboutUserByUserIdForUser(int id) throws
            NotFoundUserInDataBaseByIdException, NoRightToPerformActionsException {
        String userLogin = returnLoginSecurityUser.getLoginOfSecurityUser();
        DaoUserWithAllInfo userSecurity = userRepository.findByLogin(userLogin);
        int userSecurityId = userSecurity.getId();
        String userSecurityRole = userSecurity.getRole();
        if (userSecurityId == id || userSecurityRole.equals(adminRole)) {
            boolean existUserInDataBase = userRepository.existsById(id);
            if (!existUserInDataBase) {
                throw new NotFoundUserInDataBaseByIdException(id);
            }
            ArrayList<TransactionAboutUserById> resultTransactionsArrayList = new ArrayList<>();
            Optional<ArrayList<DaoTransactionWithLightInformation>> listOptional = Optional.ofNullable(iDaoTransactionWithLightInformation.findAllBySenderId(id));
            if (listOptional.isPresent()) {
                for (DaoTransactionWithLightInformation transaction : listOptional.get()) {
                    TransactionAboutUserById transactionAboutUsers = new TransactionAboutUserById();
                    transactionAboutUsers.setId(transaction.getId());
                    transactionAboutUsers.setDateOfTransaction(transaction.getDateOfTransaction());
                    transactionAboutUsers.setAmountOfTransfer(transaction.getAmountOfTransfer());
                    int receiverId = transaction.getReceiverId();
                    transactionAboutUsers.setReceiverNickname(userRepository.findById(receiverId).orElseThrow(() -> new NotFoundUserInDataBaseByIdException(receiverId)).getNickName());
                    resultTransactionsArrayList.add(transactionAboutUsers);
                }
            }
            Optional<ArrayList<TransactionAboutUserById>> resulListOptional = Optional.ofNullable(resultTransactionsArrayList);
            return resulListOptional;
        } else {
            throw new NoRightToPerformActionsException();
        }
    }
}