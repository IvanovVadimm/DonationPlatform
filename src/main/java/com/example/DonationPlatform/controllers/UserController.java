package com.example.DonationPlatform.controllers;

import com.example.DonationPlatform.domain.create.CardForUserView;
import com.example.DonationPlatform.domain.create.CreateUserByAdmin;
import com.example.DonationPlatform.domain.daocard.DaoCard;
import com.example.DonationPlatform.domain.daotransaction.DaoTransactionWithAllInfo;
import com.example.DonationPlatform.domain.daouser.DaoUserWithAllInfo;
import com.example.DonationPlatform.domain.request.RegistrationOfUsers;
import com.example.DonationPlatform.domain.response.TransactionAboutUserById;
import com.example.DonationPlatform.domain.update.UpdateUserByAdmin;
import com.example.DonationPlatform.domain.update.UpdateUserByUser;
import com.example.DonationPlatform.exceptions.cardsExceptions.AttemptToReplenishTheAccountWithANonExistedCardException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardExpiredException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardNotFoundExceptionByCardNumberException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardWasDeletedException;
import com.example.DonationPlatform.exceptions.usersExceptions.InvalidCreateUserException;
import com.example.DonationPlatform.exceptions.usersExceptions.NoRightToPerformActionsException;
import com.example.DonationPlatform.exceptions.usersExceptions.NotFoundUserInDataBaseByIdException;
import com.example.DonationPlatform.exceptions.usersExceptions.UserIsAlreadyExistInDataBaseWithEmailException;
import com.example.DonationPlatform.exceptions.usersExceptions.UserIsAlreadyExistInDataBaseWithLoginException;
import com.example.DonationPlatform.exceptions.usersExceptions.UserIsAlreadyExistInDataBaseWithNickNameException;
import com.example.DonationPlatform.services.TransactionService;
import com.example.DonationPlatform.services.UserService;
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
    private final UserService userService;
    private final TransactionService transactionService;

    @Autowired
    public UserController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @Operation(summary = "This method will created user by sign up")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User was created successfully"),
            @ApiResponse(responseCode = "204", description = "User was not created")
    })
    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody RegistrationOfUsers registrationOfUsers, BindingResult bindingResult) throws UserIsAlreadyExistInDataBaseWithLoginException, UserIsAlreadyExistInDataBaseWithNickNameException, UserIsAlreadyExistInDataBaseWithEmailException, InvalidCreateUserException {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn("BindingResult has Error: " + o);
                throw new InvalidCreateUserException();
            }
        }
        if (userService.userRegistration(registrationOfUsers)) {
            log.info("User was created");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            log.error("User was not created!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "This method will find user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found"),
            @ApiResponse(responseCode = "204", description = "User wasn't found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DaoUserWithAllInfo> getUserById(@PathVariable int id) throws NotFoundUserInDataBaseByIdException, NoRightToPerformActionsException {
        Optional<DaoUserWithAllInfo> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            log.info("User with id: " + id + " was found.");
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else {
            log.error("User with id: " + id + " was not found!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "This method will find all of user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found"),
            @ApiResponse(responseCode = "204", description = "User wasn't found"),
    })
    @GetMapping("/all")
    public ResponseEntity<List<DaoUserWithAllInfo>> getAllUser() {
        Optional<List<DaoUserWithAllInfo>> optionalListOfUsers = userService.getAllUser();
        if (optionalListOfUsers.isPresent()) {
            log.info("Getting about all of users is successfully");
            return new ResponseEntity<>(optionalListOfUsers.get(), HttpStatus.OK);
        } else {
            log.error("Getting about all of users is unsuccessfully!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "This method allows update data of user by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data was updated"),
            @ApiResponse(responseCode = "204", description = "User data wasn't updated"),
    })
    @PutMapping("/updateByAdmin")
    public ResponseEntity updateUserByAdmin(@RequestBody UpdateUserByAdmin user) throws NotFoundUserInDataBaseByIdException, UserIsAlreadyExistInDataBaseWithNickNameException, UserIsAlreadyExistInDataBaseWithLoginException, UserIsAlreadyExistInDataBaseWithEmailException {
        boolean resultOfUpdate = userService.updateUserByAdmin(user);
        if (resultOfUpdate) {
            log.info("User information have be just updated");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.error("User information have not been updated");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "This method allows update data of user by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data was updated"),
            @ApiResponse(responseCode = "204", description = "User data wasn't updated")
    })
    @PutMapping("/update")
    public ResponseEntity updateUserByUser(@RequestBody UpdateUserByUser user) throws NoRightToPerformActionsException, UserIsAlreadyExistInDataBaseWithLoginException, UserIsAlreadyExistInDataBaseWithNickNameException, NotFoundUserInDataBaseByIdException, UserIsAlreadyExistInDataBaseWithEmailException {
        boolean resulOfUpdate = userService.updateUserByUser(user);
        if (resulOfUpdate) {
            log.info("User information have be just updated");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.error("User information have not be updated");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "This method allows to create user by admin without checkout to registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User have created successfully."),
            @ApiResponse(responseCode = "204", description = "User have not created!"),
            @ApiResponse(responseCode = "400", description = "Error of validation!"),
    })
    @PostMapping
    public ResponseEntity createUser(@RequestBody CreateUserByAdmin user, BindingResult bindingResult) throws UserIsAlreadyExistInDataBaseWithNickNameException, UserIsAlreadyExistInDataBaseWithLoginException, UserIsAlreadyExistInDataBaseWithEmailException {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn("BindingResult has Error: " + o);
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        Optional<DaoUserWithAllInfo> optionalUser = Optional.ofNullable(userService.createUser(user));
        if (optionalUser.isPresent()) {
            log.info("User have created successfully.");
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            log.error("User have not created!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "This method allows to delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was deleted"),
            @ApiResponse(responseCode = "204", description = "User wasn't deleted")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) throws NoRightToPerformActionsException{
        if (userService.deleteUser(id)) {
            log.info("User with id: " + id + "have just deleted");
            return new ResponseEntity(HttpStatus.OK);
        } else {
            log.error("User with id: " + id + "have not deleted yet!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "This method allows to get all cards of user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cards of users was found"),
            @ApiResponse(responseCode = "204", description = "Not found user in Data base"),
    })
    @GetMapping("/allCardsOfUser/{id}")
    public ResponseEntity<ArrayList<DaoCard>> getCardsOfUserByIdOfUser(@PathVariable int id) throws NoRightToPerformActionsException{
        Optional<ArrayList<DaoCard>> cardListOptional = Optional.ofNullable(userService.getCardsOfUserByIdOfUser(id));
        if (cardListOptional.isPresent()) {
            log.info("Getting a list of cards for a user with id: " + id);
            return new ResponseEntity<>(cardListOptional.get(), HttpStatus.OK);
        } else {
            log.error("Failed to get list of user cards with id: " + id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "This method allows to get small info all transaction of user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions of users was found"),
            @ApiResponse(responseCode = "204", description = "Not found transactions in Data base")
    })
    @GetMapping("/allTransactionForUser/{id}")
    public ResponseEntity<ArrayList<TransactionAboutUserById>> getAllInfoAboutTransactionByUserIdForUser(@PathVariable int id)
            throws NotFoundUserInDataBaseByIdException, NoRightToPerformActionsException {
        Optional<ArrayList<TransactionAboutUserById>> optionalTransactionsAboutUser = transactionService.getAllOfTransactionAboutUserByUserIdForUser(id);
        if (optionalTransactionsAboutUser.isPresent()) {
            log.info("Getting a list of transaction for a user with id: " + id);
            return new ResponseEntity<>(optionalTransactionsAboutUser.get(), HttpStatus.OK);
        } else {
            log.error("Failed to get list of user transaction for a user with id: " + id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "This method allows to get all info transaction of user by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction of users was found"),
            @ApiResponse(responseCode = "204", description = "Not found transactions in Data base"),
    })
    @GetMapping("/allTransactionForAdmin/{id}")
    public ResponseEntity<ArrayList<DaoTransactionWithAllInfo>> getAllInfoAboutTransactionByUserIdForAdmin(
            @PathVariable int id) throws NotFoundUserInDataBaseByIdException {
        Optional<ArrayList<DaoTransactionWithAllInfo>> optionalTransactionAboutUsers = userService.getAllOfTransactionAboutUserByIdForAdmin(id);
        if (optionalTransactionAboutUsers.isPresent()) {
            log.info("Getting a list of transaction for an admin with user id: " + id);
            return new ResponseEntity<>(optionalTransactionAboutUsers.get(), HttpStatus.OK);
        } else {
            log.error("Failed to get list of user transaction for admin with user id: " + id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "This method allows to put money on account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Putting money on account is successfully "),
            @ApiResponse(responseCode = "204", description = "Putting wasn't successfully")
    })
    @PutMapping("/putMoney/{sum}")
    public ResponseEntity putMoneyOnAccountByCard(@RequestBody CardForUserView card,
                                                  @PathVariable @Parameter int sum) throws CardExpiredException, AttemptToReplenishTheAccountWithANonExistedCardException, CardWasDeletedException, CardNotFoundExceptionByCardNumberException{
        if (userService.putMoneyOnCurrentAmount(sum, card)) {
            log.info("Getting money on account by card with number: " + card.getNumberOfCard());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.error("Failed to get money on account by card with number: " + card.getNumberOfCard());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}