package com.example.DonationPlatform.controllers;

import com.example.DonationPlatform.domain.create.CreateTransactionByUser;
import com.example.DonationPlatform.domain.daotransaction.DaoTransactionWithLightInformation;
import com.example.DonationPlatform.exceptions.transactionsExceptions.FailCreateTransactionByDeletedReceiverException;
import com.example.DonationPlatform.exceptions.transactionsExceptions.FailCreateTransactionBySenderDontHaveEnoughSumOnAccount;
import com.example.DonationPlatform.exceptions.transactionsExceptions.TransactionNotFoundByIdExceptions;
import com.example.DonationPlatform.exceptions.usersExceptions.NoRightToPerformActionsException;
import com.example.DonationPlatform.exceptions.usersExceptions.NotFoundUserInDataBaseByIdException;
import com.example.DonationPlatform.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/transaction")
public class TransactionController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "This method gets transaction by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction was found"),
            @ApiResponse(responseCode = "204", description = "Transaction was not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DaoTransactionWithLightInformation> getTransactionById(@PathVariable int id) throws TransactionNotFoundByIdExceptions, NoRightToPerformActionsException {
        Optional<DaoTransactionWithLightInformation> transaction = transactionService.getTransactionWithLightInformationById(id);
        if (transaction.isPresent()) {
            log.info("Getting information about transaction for a user with id: " + id);
            return new ResponseEntity<>(transaction.get(), HttpStatus.OK);
        } else {
            log.error("Failed to get information about transaction of user with id: " + id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "This method creates transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction have been created"),
            @ApiResponse(responseCode = "204", description = "Transaction have not been created")
    })
    @PostMapping
    public ResponseEntity createTransaction(@RequestBody CreateTransactionByUser transaction) throws FailCreateTransactionBySenderDontHaveEnoughSumOnAccount, FailCreateTransactionByDeletedReceiverException, NotFoundUserInDataBaseByIdException, NoRightToPerformActionsException {
        if (transactionService.createTransaction(transaction)) {
            log.info("Transaction created successfully!");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            log.error("Failed creating transaction!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}