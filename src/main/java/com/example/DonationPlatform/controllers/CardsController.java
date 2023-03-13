package com.example.DonationPlatform.controllers;

import com.example.DonationPlatform.services.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;

@Controller
@RequestMapping("/cards")
public class CardsController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    CardService cardService;

    @Autowired
    public CardsController(CardService cardService) {
        this.cardService = cardService;
    }


    @GetMapping("/allCardsOfUser/{id}")
    public String getCardsOfUserByIdOfUser(@PathVariable int id, Model model) {

        ArrayList<String> arrayList = cardService.getCardsOfUserByIdOfUser(id);
        model.addAttribute("cards", arrayList);
        return "allCardsOfUser";
    }

    @GetMapping("/checkCardInDatabase/{numberOfCard}")
    public String checkCard(@PathVariable String numberOfCard) {
        if (cardService.checkCardInDataBase(numberOfCard)) {
            return "successfully";
        } else {
            return "unsuccessfully";
        }
    }

    @PostMapping("/addCard/{numberOfCard}/{expireDate}")
    public String createCard(@PathVariable String numberOfCard, @PathVariable String expireDate) {
        if (cardService.creatCardInDatabase(String.valueOf(numberOfCard), new Date(22222222))) {
            return "successfully";
        } else {
            return "unsuccessfully";
        }
    }
}