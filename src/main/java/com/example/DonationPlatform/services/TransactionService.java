package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.DAOTransaction.DAOTransactionWithAllInfo;
import com.example.DonationPlatform.domain.DAOTransaction.DAOTransactionWithLightInformation;
import com.example.DonationPlatform.domain.TransactionAboutUserById;
import com.example.DonationPlatform.repository.IDAOTransactionWithAllInformation;
import com.example.DonationPlatform.repository.IDAOTransactionWithLightInformation;
import com.example.DonationPlatform.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class TransactionService {

    private IUserRepository userRepository;

    private IDAOTransactionWithLightInformation iDaoTransactionWithLightInformation;
    private IDAOTransactionWithAllInformation iDaoTransactionRepositoryWithAllInformation;

    @Autowired
    public TransactionService(IUserRepository userRepository, IDAOTransactionWithLightInformation iDaoTransactionWithLightInformation, IDAOTransactionWithAllInformation iDaoTransactionRepositoryWithAllInformation) {
        this.userRepository = userRepository;
        this.iDaoTransactionWithLightInformation = iDaoTransactionWithLightInformation;
        this.iDaoTransactionRepositoryWithAllInformation = iDaoTransactionRepositoryWithAllInformation;
    }

    @Transactional
    public boolean createTransaction(DAOTransactionWithLightInformation transaction) {

        int senderId = transaction.getSenderId();
        int receiverId = transaction.getReceiverId();
        double amountOfTransfer = transaction.getAmountOfTransfer();

        if (iDaoTransactionWithLightInformation.countOnAmountIsMoreThanOnOperation(senderId, amountOfTransfer)
                && userRepository.existsById(receiverId)) {

            int sumOnCurrentAmountOnAccount = userRepository.findById(senderId).get().getCurrentAmountOnAccount();

            if (sumOnCurrentAmountOnAccount - transaction.getAmountOfTransfer() >= 0) {
                iDaoTransactionWithLightInformation.withdrawalCashFromAmount(amountOfTransfer, senderId);
                iDaoTransactionWithLightInformation.gettingMoneyIntoAnAccount(amountOfTransfer, receiverId);
                iDaoTransactionWithLightInformation.save(transaction);
                return true;
            }
        }
        return false;
    }

    public Optional<DAOTransactionWithLightInformation> getIDaoTransactionWithLightInformationById(int id) {
        Optional<DAOTransactionWithLightInformation> transactionOptional = Optional.ofNullable(iDaoTransactionWithLightInformation.findById(id).get());
        return transactionOptional;
    }

    public Optional<ArrayList<TransactionAboutUserById>> getAllOfTransactionAboutUserByUserIdForUser(int id) {
        ArrayList<TransactionAboutUserById> resultTransactionsArrayList = new ArrayList<>();
        Optional<ArrayList<DAOTransactionWithLightInformation>> listOptional = Optional.ofNullable(iDaoTransactionWithLightInformation.findAllBySenderId(id));
        if (listOptional.isPresent()) {
            for (DAOTransactionWithLightInformation transaction : listOptional.get()) {
                TransactionAboutUserById transactionAboutUsers = new TransactionAboutUserById();
                transactionAboutUsers.setId(transaction.getId());
                transactionAboutUsers.setDateOfTransaction(transaction.getDateOfTransaction());
                transactionAboutUsers.setAmountOfTransfer(transaction.getAmountOfTransfer());
                int receiverId = transaction.getReceiverId();
                transactionAboutUsers.setReceiverNickname(userRepository.findById(receiverId).get().getNickName());
                resultTransactionsArrayList.add(transactionAboutUsers);
            }
        }
        Optional<ArrayList<TransactionAboutUserById>> resulListOptional = Optional.ofNullable(resultTransactionsArrayList);
        return resulListOptional;
    }

    public Optional<ArrayList<DAOTransactionWithAllInfo>> getAllOfTransactionAboutUserByIdForAdmin(int id) {
        Optional<ArrayList<DAOTransactionWithAllInfo>> listOptional = Optional.ofNullable(iDaoTransactionRepositoryWithAllInformation.findAllBySenderId(id));
        return listOptional;
    }
}