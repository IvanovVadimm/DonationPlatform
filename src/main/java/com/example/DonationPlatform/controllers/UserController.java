package com.example.DonationPlatform.controllers;

import com.example.DonationPlatform.domain.User;
import com.example.DonationPlatform.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            log.info("User was founded!");
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        } else {
            log.info("User was not founded!");
            return new ResponseEntity<>(HttpStatus.FOUND);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<ArrayList<User>> getAllUser() {
        ArrayList<User> userArrayList = userService.getAllUser();
        if (userArrayList != null) {
            log.info("Users were founded!");
            return new ResponseEntity<>(userArrayList, HttpStatus.FOUND);
        } else {
            log.info("Users were not founded!");
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody User user) {
        boolean result;
        result = userService.updateUser(user);
        if (result) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            log.warn("User was not updated!");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn("We have bindingResult error : " + o);
            }
        }
        return (userService.createUser(user)) ? new ResponseEntity(user, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn("We have bindingResult error : " + o);
            }
        }
        return (userService.deleteUser(user)) ? new ResponseEntity(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.OK);
    }
}