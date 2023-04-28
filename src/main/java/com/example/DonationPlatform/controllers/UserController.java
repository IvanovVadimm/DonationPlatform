package com.example.DonationPlatform.controllers;

import com.example.DonationPlatform.domain.User;
import com.example.DonationPlatform.domain.request.RegistrationOfUsers;
import com.example.DonationPlatform.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        Optional<User> userOptional = Optional.ofNullable(user);
        // TODO: сделать проверку на то что какое-то из полей пользователя совпадает с тем что уже в базе
        if (userOptional.isPresent()) {
            //log through aspects in future
            return new ResponseEntity<>(userOptional.get(), HttpStatus.FOUND);
        } else {
            //log through aspects in future
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<ArrayList<User>> getAllUser() {
        Optional<ArrayList<User>> optionalArrayListOfUsers = Optional.ofNullable(userService.getAllUser());
        if (optionalArrayListOfUsers.isPresent()) {
            //log through aspects in future
            return new ResponseEntity<>(optionalArrayListOfUsers.get(), HttpStatus.FOUND);
        } else {
            //log through aspects in future
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody User user) {
        Optional<User> optionalUser = Optional.of(userService.updateUser(user));
        if (optionalUser.isPresent()) {
            //log through aspects in future
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            //log through aspects in future
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                ////log through aspects in future
                //log.warn("We have bindingResult error : " + o);
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        Optional<User> optionalUser = Optional.ofNullable(userService.createUser(user));
        if (optionalUser.isPresent()) {
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
}