package com.example.DonationPlatform.controllers;


import com.example.DonationPlatform.domain.CardForUsersView;
import com.example.DonationPlatform.domain.DAOCard.DAOCard;
import com.example.DonationPlatform.domain.DAOTransaction.DAOTransactionWithAllInfo;
import com.example.DonationPlatform.domain.DAOUser.DAOUserWithAllInfo;
import com.example.DonationPlatform.domain.TransactionAboutUserById;
import com.example.DonationPlatform.domain.request.RegistrationOfUsers;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardExpiredException;
import com.example.DonationPlatform.services.CardService;
import com.example.DonationPlatform.services.TransactionService;
import com.example.DonationPlatform.services.UserService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private CardService cardService;

    private UserService userService;

    private TransactionService transactionService;

    @Autowired
    public UserController(CardService cardService, UserService userService, TransactionService transactionService) {
        this.cardService = cardService;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody RegistrationOfUsers registrationOfUsers) {
        if (userService.userRegistration(registrationOfUsers)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DAOUserWithAllInfo> getUserById(@PathVariable int id) {
        Optional<DAOUserWithAllInfo> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<DAOUserWithAllInfo>> getAllUser() {
        Optional<List<DAOUserWithAllInfo>> optionalListOfUsers = userService.getAllUser();
        if (optionalListOfUsers.isPresent()) {
            return new ResponseEntity<>(optionalListOfUsers.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody DAOUserWithAllInfo user) {
        Optional<DAOUserWithAllInfo> optionalUser = Optional.of(userService.updateUser(user));
        if (optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody DAOUserWithAllInfo user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        Optional<DAOUserWithAllInfo> optionalUser = Optional.ofNullable(userService.createUser(user));
        if (optionalUser.isPresent() && optionalUser.get().getId() != 0) {
            //log through aspects in future
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            //log through aspects in future
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {

        if (userService.deleteUser(id)) {
            ////log through aspects in future
            return new ResponseEntity(HttpStatus.OK);
        } else {
            //log through aspects in future
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/aoubi/{id}") // получение всех карточек пользователя по id
    public ResponseEntity<ArrayList<DAOCard>> getCardsOfUserByIdOfUser(@PathVariable int id) {
        ArrayList<DAOCard> arrayList = cardService.getCardsOfUserByIdOfUser(id);
        return new ResponseEntity<>(arrayList, HttpStatus.OK);
    }

    @GetMapping("/alltr/{id}")
    public ResponseEntity<ArrayList<TransactionAboutUserById>> getAllInfoAboutTransactionByUserIdForUser(@PathVariable int id) {
        Optional<ArrayList<TransactionAboutUserById>> optionalTransactionsAboutUser = transactionService.getAllOfTransactionAboutUserByUserIdForUser(id);
        if (optionalTransactionsAboutUser.isPresent()) {
            return new ResponseEntity<>(optionalTransactionsAboutUser.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/alltrforadmin/{id}")
    public ResponseEntity<ArrayList<DAOTransactionWithAllInfo>> getAllInfoAboutTransactionByUserIdForAdmin(@PathVariable int id) {
        Optional<ArrayList<DAOTransactionWithAllInfo>> optionalTransactionAboutUsers = transactionService.getAllOfTransactionAboutUserByIdForAdmin(id);
        if (optionalTransactionAboutUsers.isPresent()) {
            return new ResponseEntity<>(optionalTransactionAboutUsers.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/tuya/{userId}/{sum}")
    public ResponseEntity putMoneyOnAccountByCard(@RequestBody CardForUsersView card, @PathVariable int userId, @PathVariable int sum) throws CardExpiredException {
        //try {
        if (cardService.checkCardInDataBase(card.getNumberOfCard())) {
            if (!cardService.cardIsExpired(card)) {
                if (userService.putMoneyOnCurrentAmount(sum, userId, card)) {
                    return new ResponseEntity<>(HttpStatus.CREATED);
                }
            }
        /*} catch (CardExpiredException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return null;*/

        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}