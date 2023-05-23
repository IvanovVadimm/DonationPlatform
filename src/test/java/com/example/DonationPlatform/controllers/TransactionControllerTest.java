package com.example.DonationPlatform.controllers;

import com.example.DonationPlatform.domain.create.CardForUserView;
import com.example.DonationPlatform.domain.create.CreateTransactionByUser;
import com.example.DonationPlatform.domain.daocard.DaoCard;
import com.example.DonationPlatform.domain.daotransaction.DaoTransactionWithAllInfo;
import com.example.DonationPlatform.domain.daotransaction.DaoTransactionWithLightInformation;
import com.example.DonationPlatform.domain.daouser.DaoUserWithAllInfo;
import com.example.DonationPlatform.services.TransactionService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {
    @InjectMocks
    private TransactionController transactionController;
    private MockMvc mockMvc;
    @Mock
    private TransactionService transactionService;
    private final DaoCard card = new DaoCard();
    private final DaoTransactionWithAllInfo daoTransactionWithAllInfo = new DaoTransactionWithAllInfo();
    private final DaoTransactionWithLightInformation daoTransactionWithLightInformation = new DaoTransactionWithLightInformation();
    private final CreateTransactionByUser createTransactionByUser = new CreateTransactionByUser();
    private final CardForUserView cardForUserView = new CardForUserView();
    private final DaoUserWithAllInfo user1 = new DaoUserWithAllInfo();
    private final DaoUserWithAllInfo user2 = new DaoUserWithAllInfo();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
        card.setNumberOfCard("4111222233334444");
        card.setId(1);
        card.setCvv("441");
        card.setExpireDate(Date.valueOf("2029-05-12"));
        cardForUserView.setExpireDate(card.getExpireDate());
        cardForUserView.setNumberOfCard(card.getNumberOfCard());
        daoTransactionWithAllInfo.setDateOfTransaction(new Date(new java.util.Date().getTime()));
        daoTransactionWithAllInfo.setId(1);
        daoTransactionWithAllInfo.setAmountOfTransfer(100);
        daoTransactionWithAllInfo.setSender(user1);
        daoTransactionWithAllInfo.setReceiver(user2);
        daoTransactionWithLightInformation.setSenderId(daoTransactionWithAllInfo.getSender().getId());
        daoTransactionWithLightInformation.setReceiverId(daoTransactionWithAllInfo.getReceiver().getId());
        daoTransactionWithLightInformation.setDateOfTransaction(daoTransactionWithAllInfo.getDateOfTransaction());
        daoTransactionWithLightInformation.setAmountOfTransfer(daoTransactionWithAllInfo.getAmountOfTransfer());
        createTransactionByUser.setReceiverId(daoTransactionWithAllInfo.getReceiver().getId());
        createTransactionByUser.setDateOfTransaction(daoTransactionWithAllInfo.getDateOfTransaction());
        createTransactionByUser.setAmountOfTransfer(daoTransactionWithAllInfo.getAmountOfTransfer());
    }

    @Test
    void getTransactionById() throws Exception {
        when(transactionService.getTransactionWithLightInformationById(anyInt())).thenReturn(Optional.of(daoTransactionWithLightInformation));
        MvcResult result = mockMvc.perform(get("/transaction/{id}", anyInt()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(transactionService, times(1)).getTransactionWithLightInformationById(anyInt());

    }

    @Test
    void createTransaction() throws Exception {
        when(transactionService.createTransaction(createTransactionByUser)).thenReturn(true);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(createTransactionByUser);
        MvcResult result = mockMvc.perform(post("/transaction").contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(transactionService, times(1)).createTransaction(any());
    }
}