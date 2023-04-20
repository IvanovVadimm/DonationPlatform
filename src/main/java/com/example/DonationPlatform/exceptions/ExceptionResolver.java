package com.example.DonationPlatform.exceptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionResolver {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ArithmeticException.class)
    public String muFirstExHand(Exception e){
        log.warn("ArithmeticException: "     + e);
        return "unsuccessfully";
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<HttpStatus> usernameNotFound(Exception e){
        log.warn("UsernameNotFoundException: " + e.getMessage());
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
