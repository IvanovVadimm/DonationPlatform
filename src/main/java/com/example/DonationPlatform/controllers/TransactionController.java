package com.example.DonationPlatform.controllers;

import com.example.DonationPlatform.domain.DAOTransaction.DAOTransactionWithLightInformation;
import com.example.DonationPlatform.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<DAOTransactionWithLightInformation> getTransactionById(@PathVariable int id){
        Optional<DAOTransactionWithLightInformation> transaction = transactionService.getIDaoTransactionWithLightInformationById(id);
        if (transaction.isPresent()){
            return new ResponseEntity<>(transaction.get(),HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @PostMapping
    public ResponseEntity createTransaction(@RequestBody DAOTransactionWithLightInformation transaction) {
        if (transactionService.createTransaction(transaction)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}

