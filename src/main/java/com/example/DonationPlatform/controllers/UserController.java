package com.example.DonationPlatform.controllers;

import com.example.DonationPlatform.domain.User;
import com.example.DonationPlatform.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "singleUser"; // возвращает jsp
    }


    @PutMapping
    public String updateUser(int id, String email, String login, String nickname, String password) {
        boolean result;
        result = userService.updateUser(id, email, login, nickname, password);
        return (result) ? "successfully" : "unsuccessfully";
    }


    @GetMapping
    public String createUser(@ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn("We have bindingResult error : " + o);
            }
            return "unsuccessfully";
        }
        boolean result = userService.createUser(user);
        return result ? "successfully" : "unsuccessfully";
    }
}
