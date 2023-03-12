package com.example.DonationPlatform.controllers;

import com.example.DonationPlatform.repository.CardRepository;
import com.example.DonationPlatform.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/cards")
public class CardsController {

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
}
