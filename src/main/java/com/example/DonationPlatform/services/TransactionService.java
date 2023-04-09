package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.Transaction;
import com.example.DonationPlatform.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;


    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public boolean createTransaction(Transaction transaction) {
        return transactionRepository.createTransaction(transaction);
    }

    public Transaction getTransactionById(int id){
        return transactionRepository.getTransactionById(id);
    }
}
