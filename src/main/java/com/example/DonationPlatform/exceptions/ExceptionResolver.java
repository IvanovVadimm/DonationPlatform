package com.example.DonationPlatform.exceptions;

import com.example.DonationPlatform.exceptions.cardsExceptions.AttemptToReplenishTheAccountWithANonExistedCardException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardAlreadyExistsInDataBaseException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardAlreadyExistsInDataBaseWithCvvException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardExpiredException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardNotFoundByCardIdException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardNotFoundExceptionByCardNumberException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardWasDeletedException;
import com.example.DonationPlatform.exceptions.cardsExceptions.InvalidCreateCardOfUserExceptionByCardNumberAndExpiredDate;
import com.example.DonationPlatform.exceptions.cardsExceptions.NotEnteredCardNumberException;
import com.example.DonationPlatform.exceptions.cardsExceptions.NullPointerInCreatingCardException;
import com.example.DonationPlatform.exceptions.transactionsExceptions.CreatedTransactionForSameAccountException;
import com.example.DonationPlatform.exceptions.transactionsExceptions.FailCreateTransactionByDeletedReceiverException;
import com.example.DonationPlatform.exceptions.transactionsExceptions.FailCreateTransactionBySenderDontHaveEnoughSumOnAccount;
import com.example.DonationPlatform.exceptions.transactionsExceptions.TransactionNotFoundByIdExceptions;
import com.example.DonationPlatform.exceptions.usersExceptions.InvalidCreateUserException;
import com.example.DonationPlatform.exceptions.usersExceptions.NoRightToPerformActionsException;
import com.example.DonationPlatform.exceptions.usersExceptions.NotFoundUserInDataBaseByIdException;
import com.example.DonationPlatform.exceptions.usersExceptions.NotFoundUserInDataBaseByLoginException;
import com.example.DonationPlatform.exceptions.usersExceptions.UserIsAlreadyExistInDataBaseWithEmailException;
import com.example.DonationPlatform.exceptions.usersExceptions.UserIsAlreadyExistInDataBaseWithLoginException;
import com.example.DonationPlatform.exceptions.usersExceptions.UserIsAlreadyExistInDataBaseWithNickNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionResolver {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(NoRightToPerformActionsException.class)
    public ResponseEntity<String> NoRightToPerformActionsHand(NoRightToPerformActionsException e) {
        log.error("NoRightToPerformActions: " + e + " in " + e.getClass());
        String message = "Not right to do this!";
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidCreateUserException.class)
    public ResponseEntity<String> InvalidCreateUserExceptionHand(InvalidCreateUserException e) {
        log.error("InvalidCreateUserException: " + e + " in " + e.getClass());
        String message = "You entered incorrect data!";
        return new ResponseEntity<>(message, HttpStatus.NOT_IMPLEMENTED);
    }


    @ExceptionHandler(CardExpiredException.class)
    public ResponseEntity<String> CardExpiredExceptionHand(CardExpiredException e) {
        log.error("CardExpiredException: " + e + " in " + e.getClass());
        String message = "This card was expired!";
        return new ResponseEntity<>(message, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(CardAlreadyExistsInDataBaseException.class)
    public ResponseEntity<String> CardAlreadyExistsInDataBaseExceptionHand(CardAlreadyExistsInDataBaseException e) {
        log.error("CardAlreadyExistsInDataBaseException: " + e + " in " + e.getClass());
        String message = "This card was existed before!";
        return new ResponseEntity<>(message, HttpStatus.FOUND);
    }

    @ExceptionHandler(NotEnteredCardNumberException.class)
    public ResponseEntity<String> NotEnteredCardNumberExceptionHand(NotEnteredCardNumberException e) {
        log.error("NotEnteredCardNumberException: " + e + " in " + e.getClass());
        String message = "Enter card number!";
        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(CardNotFoundExceptionByCardNumberException.class)
    public ResponseEntity<String> CardNotFoundExceptionHand(CardNotFoundExceptionByCardNumberException e) {
        log.error("CardNotFoundExceptionByCardNumberException: " + e + " in " + e.getClass());
        String message = "This card didn't exist ever!";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CardNotFoundByCardIdException.class)
    public ResponseEntity<String> CardNotFoundExceptionByIdHand(CardNotFoundByCardIdException e) {
        log.error("CardNotFoundExceptionByCardIdException: " + e + " in " + e.getClass());
        String message = "This card didn't exist ever!";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCreateCardOfUserExceptionByCardNumberAndExpiredDate.class)
    public ResponseEntity<String> InvalidCreateCardOfUserExceptionByCardNumberAndExpiredDateHand(InvalidCreateCardOfUserExceptionByCardNumberAndExpiredDate e) {
        log.error("InvalidCreateCardOfUserExceptionByCardNumberAndExpiredDate: " + e + " in " + e.getClass());
        String message = "Card data was not validated! Check your card number and expire date then entering them again...";
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CardAlreadyExistsInDataBaseWithCvvException.class)
    public ResponseEntity<String> CardAlreadyExistsInDataBaseWithCvvExceptionHand(CardAlreadyExistsInDataBaseWithCvvException e) {
        log.error("CardAlreadyExistsInDataBaseWithCvvException: " + e + " in " + e.getClass());
        String message = "Check data your card and entering again... This card cannot be created!";
        return new ResponseEntity<>(message, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(AttemptToReplenishTheAccountWithANonExistedCardException.class)
    public ResponseEntity<String> AttemptToReplenishTheAccountWithAaNonExistentCardHand(AttemptToReplenishTheAccountWithANonExistedCardException e) {
        log.error("AttemptToReplenishTheAccountWithANonExistedCardException: " + e + " in " + e.getClass());
        String message = "Card haven't found!";
        return new ResponseEntity<>(message, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(CardWasDeletedException.class)
    public ResponseEntity<String> CardWasDeletedExceptionHand(CardWasDeletedException e) {
        log.error("CardWasDeletedException: " + e + " in " + e.getClass());
        String message = "Card haven't found!";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FailCreateTransactionByDeletedReceiverException.class)
    public ResponseEntity<String> FailCreateTransactionByDeletedReceiverExceptionHand(FailCreateTransactionByDeletedReceiverException e) {
        log.error("FailCreateTransactionByDeletedReceiverException: " + e + " in " + e.getClass());
        String message = "Receiver haven't found!";
        return new ResponseEntity<>(message, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(FailCreateTransactionBySenderDontHaveEnoughSumOnAccount.class)
    public ResponseEntity<String> FailCreateTransactionBySenderDontHaveEnoughSumOnAccountHand(FailCreateTransactionBySenderDontHaveEnoughSumOnAccount e) {
        log.error("FailCreateTransactionBySenderDontHaveEnoughSumOnAccount: " + e + " in " + e.getClass());
        String message = "You do not have enough money on your account!";
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TransactionNotFoundByIdExceptions.class)
    public ResponseEntity<String> TransactionNotFoundByIdExceptionsHand(TransactionNotFoundByIdExceptions e) {
        log.warn("TransactionNotFoundByIdExceptions: " + e + " in " + e.getClass());
        String message = "Transaction haven't found!";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CreatedTransactionForSameAccountException.class)
    public ResponseEntity<String> CreatedTransactionForSameAccountExceptionHand(CreatedTransactionForSameAccountException e) {
        log.warn("CreatedTransactionForSameAccountException: " + e + " in " + e.getClass());
        String message = "Error of transaction! Choose another user...";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundUserInDataBaseByIdException.class)
    public ResponseEntity<String> NotFoundUserInDataBaseExceptionHand(NotFoundUserInDataBaseByIdException e) {
        log.warn("NotFoundUserInDataBaseException: " + e);
        String message = "User haven't found!";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundUserInDataBaseByLoginException.class)
    public ResponseEntity<String> NotFoundUserInDataBaseByLoginExceptionHand(NotFoundUserInDataBaseByLoginException e) {
        log.warn("NotFoundUserInDataBaseByLoginException: " + e);
        String message = "User haven't found!";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserIsAlreadyExistInDataBaseWithLoginException.class)
    public ResponseEntity<String> UserIsAlreadyExistInDataBaseWithLoginExceptionHand(UserIsAlreadyExistInDataBaseWithLoginException e) {
        log.warn("UserIsAlreadyExistInDataBaseWithLoginException: " + e);
        String message = "Enter another login!";
        return new ResponseEntity<>(message, HttpStatus.FOUND);
    }

    @ExceptionHandler(UserIsAlreadyExistInDataBaseWithNickNameException.class)
    public ResponseEntity<String> UserIsAlreadyExistInDataBaseWithNickNameExceptionHand(UserIsAlreadyExistInDataBaseWithNickNameException e) {
        log.warn("UserIsAlreadyExistInDataBaseWithNickNameException: " + e);
        String message = "Enter another nickname!";
        return new ResponseEntity<>(message, HttpStatus.FOUND);
    }

    @ExceptionHandler(UserIsAlreadyExistInDataBaseWithEmailException.class)
    public ResponseEntity<String> UserIsAlreadyExistInDataBaseWithEmailExceptionHand(UserIsAlreadyExistInDataBaseWithEmailException e) {
        log.warn("UserIsAlreadyExistInDataBaseWithEmailException: " + e);
        String message = "Enter another email!";
        return new ResponseEntity<>(message, HttpStatus.FOUND);
    }

    @ExceptionHandler(NullPointerInCreatingCardException.class)
    public ResponseEntity<String> NullPointerInCreatingCardExceptionHand(NullPointerInCreatingCardException e) {
        log.warn("NullPointerInCreatingCardException: " + e);
        String message = "Entered not all data!";
        return new ResponseEntity<>(message, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<HttpStatus> usernameNotFound(Exception e) {
        log.warn("UsernameNotFoundException: " + e.getMessage());
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpStatus> IllegalArgumentExceptionHand(IllegalArgumentException e) {
        log.warn("IllegalArgumentException: " + e.getMessage());
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> ConstraintViolationExceptionHand(ConstraintViolationException e) {
        log.warn("ConstraintViolationException: " + e.getMessage());
        String message = ("You wrote invalid data! Try again...");
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }
}