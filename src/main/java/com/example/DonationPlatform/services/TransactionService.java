package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.Transaction;
import com.example.DonationPlatform.repository.ITransactionRepository;
import com.example.DonationPlatform.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private ITransactionRepository transactionRepository;
    private IUserRepository userRepository;

    @Autowired
    public TransactionService(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public boolean createTransaction(Transaction transaction) {
        if (transactionRepository.countOnAmountIsMoreThanOnOperation(transaction.getSenderId(), transaction.getAmountOfTransfer())
                && userRepository.existsById(transaction.getReceiverId())
                && userRepository.isDeletedUserInDataBaseByIdUserChecked(transaction.getReceiverId())) {
            Optional<Transaction> transactionOptional = Optional.of(transactionRepository.save(transaction));
            return transactionOptional.isPresent();
        }
        return false;
    }

    public Transaction getTransactionById(int id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        return (optionalTransaction.isPresent()) ? optionalTransaction.get() : new Transaction();
    }
}
