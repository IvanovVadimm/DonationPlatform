package com.example.DonationPlatform.controllers;

import com.example.DonationPlatform.domain.Transaction;
import com.example.DonationPlatform.repository.TransactionRepository;
import com.example.DonationPlatform.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trans")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable int id){
        Transaction transaction = transactionService.getTransactionById(id);
        if(transaction != null){
            return new ResponseEntity<>(transaction,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
    transactionService.createTransaction(transaction);
        if (transaction != null) {
            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}
