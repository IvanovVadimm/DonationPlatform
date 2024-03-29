package com.example.DonationPlatform.controllers;

import com.example.DonationPlatform.domain.create.CardForEnteringByUserAndCreateInDataBase;
import com.example.DonationPlatform.domain.create.CardForUserView;
import com.example.DonationPlatform.domain.daocard.DaoCard;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardAlreadyExistsInDataBaseException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardAlreadyExistsInDataBaseWithCvvException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardNotFoundByCardIdException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardNotFoundExceptionByCardNumberException;
import com.example.DonationPlatform.exceptions.cardsExceptions.InvalidCreateCardOfUserExceptionByCardNumberAndExpiredDate;
import com.example.DonationPlatform.exceptions.cardsExceptions.NotEnteredCardNumberException;
import com.example.DonationPlatform.exceptions.usersExceptions.NoRightToPerformActionsException;
import com.example.DonationPlatform.services.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/cards")
public class CardsController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final CardService cardService;

    @Autowired
    public CardsController(CardService cardService) {
        this.cardService = cardService;
    }

    @Operation(summary = "This method return card by entering id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This card was found"),
            @ApiResponse(responseCode = "204", description = "This card have not been found after request")
    })
    @GetMapping("/{id}")
    public ResponseEntity getCardById(@Parameter(description = "Expected card id") @PathVariable int id) throws CardNotFoundByCardIdException, NoRightToPerformActionsException {
        Optional<DaoCard> cardOptional = cardService.getCardById(id);
        if (cardOptional.isPresent()) {
            log.info("Getting information about card  with id: " + id);
            return new ResponseEntity<>(cardOptional.get(), HttpStatus.OK);
        } else {
            log.error("Failed to get information about card  with id: " + id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "This method will created card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Card was created successfully"),
            @ApiResponse(responseCode = "204", description = "Card have not been created"),
    })
    @PostMapping
    public ResponseEntity createCard(@RequestBody @Valid CardForEnteringByUserAndCreateInDataBase card, BindingResult bindingResult) throws InvalidCreateCardOfUserExceptionByCardNumberAndExpiredDate, CardAlreadyExistsInDataBaseException, CardAlreadyExistsInDataBaseWithCvvException {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn("BindingResult has Error: " + o);
                throw new InvalidCreateCardOfUserExceptionByCardNumberAndExpiredDate();
            }
        }
        if (cardService.createCardInDatabase(card)) {
            log.info("Creating card is successfully!");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            log.error("Creating card is unsuccessfully!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "This method allows to delete card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card was deleted"),
            @ApiResponse(responseCode = "204", description = "Card have not been deleted")
    })
    @DeleteMapping
    public ResponseEntity deleteCard(@RequestBody CardForUserView card) throws CardNotFoundExceptionByCardNumberException, NotEnteredCardNumberException, NoRightToPerformActionsException {
        if (cardService.deleteCardOfUserByCardNumber(card)) {
            log.info("Deleting card is successfully!");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.error("Deleting card is not successfully!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}