
package com.example.DonationPlatform.controllers;

import com.example.DonationPlatform.domain.Card;
import com.example.DonationPlatform.services.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/cards")
public class CardsController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private CardService cardService;

    @Autowired
    public CardsController(CardService cardService) {
        this.cardService = cardService;
    }


    @GetMapping("/aoubi/{id}") // получение всех карточек пользователя по id
    public ResponseEntity<ArrayList<Card>> getCardsOfUserByIdOfUser(@PathVariable int id) {
        ArrayList<Card> arrayList = cardService.getCardsOfUserByIdOfUser(id);
        return new ResponseEntity<>(arrayList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCardById(@PathVariable int id) {

        Optional<Card> cardOptional = cardService.getCardById(id);
        if (cardOptional.isPresent()) {
            return new ResponseEntity<>(cardOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/ссidb/{numberOfCard}") // проверка существует ли данная карта в базе данных
    public ResponseEntity checkCard(@PathVariable String numberOfCard) {
        if (cardService.checkCardInDataBase(numberOfCard)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping // добавление новой карты
    public ResponseEntity createCard(@RequestBody Card card, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn("BindingResult has Error: " + o);
            }
        }
        if (cardService.creatCardInDatabase(card)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.OK); // неправильные данные карты
        }
    }

    @DeleteMapping
    public ResponseEntity deleteCard(@RequestBody Card card) {
        if (cardService.deleteCardOfUserByCardNumber(card)) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
