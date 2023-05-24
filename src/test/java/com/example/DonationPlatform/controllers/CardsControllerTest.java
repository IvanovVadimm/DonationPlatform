package com.example.DonationPlatform.controllers;

import com.example.DonationPlatform.domain.create.CardForUserView;
import com.example.DonationPlatform.domain.daocard.DaoCard;
import com.example.DonationPlatform.services.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CardsControllerTest {
    @InjectMocks
    private CardsController cardsController;
    private MockMvc mockMvc;
    @Mock
    private CardService cardService;
    private final DaoCard card = new DaoCard();
    private final CardForUserView cardForUserView = new CardForUserView();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(cardsController).build();
        card.setNumberOfCard("4111222233334444");
        card.setId(1);
        card.setCvv("441");
        card.setExpireDate(Date.valueOf("2029-05-12"));
        cardForUserView.setExpireDate(card.getExpireDate());
        cardForUserView.setNumberOfCard(card.getNumberOfCard());
    }

    @Test
    void getCardById() throws Exception {
        when(cardService.getCardById(anyInt())).thenReturn(Optional.of(card));
        MvcResult result = mockMvc.perform(get("/cards/{id}", anyInt()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(cardService, times(1)).getCardById(anyInt());
    }

    @Test
    void createCard() throws Exception {
        when(cardService.createCardInDatabase(any())).thenReturn(true);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(card);
        MvcResult result = mockMvc.perform(post("/cards").contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(cardService, times(1)).createCardInDatabase(any());
    }

    @Test
    void deleteCard() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(card);
        mockMvc.perform(delete("/cards").contentType(APPLICATION_JSON).content(requestJson))
                .andExpect(status().isNoContent())
                .andReturn();
        verify(cardService, times(1)).deleteCardOfUserByCardNumber(cardForUserView);
    }
}