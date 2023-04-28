
package com.example.DonationPlatform.controllers;

import com.example.DonationPlatform.domain.Transaction;
import com.example.DonationPlatform.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/trans")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable int id) {
        Optional<Transaction> optionalTransaction = Optional.ofNullable(transactionService.getTransactionById(id));
        if (optionalTransaction.isPresent()) {
            return new ResponseEntity<>(optionalTransaction.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    public ResponseEntity createTransaction(@RequestBody Transaction transaction) {
        if (transactionService.createTransaction(transaction)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

}

