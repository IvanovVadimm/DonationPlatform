package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.Transaction;
import com.example.DonationPlatform.repository.ITransactionRepository;
import com.example.DonationPlatform.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionService {

    private ITransactionRepository transactionRepository;
    private IUserRepository userRepository;

    @Autowired
    public TransactionService(ITransactionRepository transactionRepository, IUserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public boolean createTransaction(Transaction transaction) {
        boolean resultOfdataMatchesOfSumms;
        boolean resultOfwithdrawal;

        if (transactionRepository.countOnAmountIsMoreThanOnOperation(transaction.getSenderId(), transaction.getAmountOfTransfer())
                && userRepository.existsById(transaction.getReceiverId())) {

            int sumOnCurrentAmountOnAccount = userRepository.findById(transaction.getSenderId()).get().getCurrentAmountOnAccount();

            if (sumOnCurrentAmountOnAccount - transaction.getAmountOfTransfer() >= 0) {
                transactionRepository.withdrawalCashFromAmount(transaction.getAmountOfTransfer(), transaction.getSenderId());
                transactionRepository.gettingMoneyIntoAnAccount(transaction.getAmountOfTransfer(), transaction.getReceiverId());
                transactionRepository.save(transaction);
                return true;
            }
        }
        return false;
    }


    public Transaction getTransactionById(int id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        return (optionalTransaction.isPresent()) ? optionalTransaction.get() : new Transaction();
    }
}
