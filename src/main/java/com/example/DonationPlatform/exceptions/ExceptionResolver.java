package com.example.DonationPlatform.exceptions;


import com.example.DonationPlatform.exceptions.cardsExceptions.CardExpiredException;
import com.example.DonationPlatform.exceptions.usersExceptions.NoRightToPerformActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AopInvocationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionResolver {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NoRightToPerformActions.class)
    public String NoRightToPerformActionsHand(NoRightToPerformActions e){
        log.warn("NoRightToPerformActions: " + e);
        return e.toString();
    }

    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(CardExpiredException.class)
    public String CardExpiredException(CardExpiredException e){
        log.warn("CardExpiredException: " + e);
        return e.toString();
    }

    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(AopInvocationException.class)
    public String CardExpiredException(AopInvocationException e){
        log.warn("AopInvocationException: " + e);
        return e.getMessage();
    }

    /*@ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<HttpStatus> usernameNotFound(Exception e){
        log.warn("UsernameNotFoundException: " + e.getMessage());
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }*/
}
