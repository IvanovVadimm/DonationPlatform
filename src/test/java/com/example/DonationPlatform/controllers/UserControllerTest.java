package com.example.DonationPlatform.controllers;

import com.example.DonationPlatform.domain.create.CardForUserView;
import com.example.DonationPlatform.domain.create.CreateUserByAdmin;
import com.example.DonationPlatform.domain.daocard.DaoCard;
import com.example.DonationPlatform.domain.daotransaction.DaoTransactionWithAllInfo;
import com.example.DonationPlatform.domain.daouser.DaoUserWithAllInfo;
import com.example.DonationPlatform.domain.request.RegistrationOfUsers;
import com.example.DonationPlatform.domain.response.TransactionAboutUserById;
import com.example.DonationPlatform.domain.update.UpdateUserByAdmin;
import com.example.DonationPlatform.domain.update.UpdateUserByUser;
import com.example.DonationPlatform.services.TransactionService;
import com.example.DonationPlatform.services.UserService;
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
import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;
    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @Mock
    private TransactionService transactionService;
    private final RegistrationOfUsers registrationOfUsers = new RegistrationOfUsers("test@mail.ru", "80291112233", "testPassword", "testNickName", new Date(new java.util.Date().getTime()));
    private final DaoUserWithAllInfo daoUserWithAllInfo = new DaoUserWithAllInfo();
    private final CreateUserByAdmin createUserByAdmin = new CreateUserByAdmin();
    private final DaoCard daoCard = new DaoCard();
    private final TransactionAboutUserById transactionAboutUserById = new TransactionAboutUserById();
    private final DaoTransactionWithAllInfo daoTransactionWithAllInfo = new DaoTransactionWithAllInfo();
    private final CardForUserView cardForUserView = new CardForUserView();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        cardForUserView.setNumberOfCard("4111222233334444");
        cardForUserView.setExpireDate(Date.valueOf("2027-03-04"));
    }

    @Test
    void registration() throws Exception {
        when(userService.userRegistration(any(RegistrationOfUsers.class))).thenReturn(true);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(registrationOfUsers);
        MvcResult result = mockMvc.perform(post("/user/registration").contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(userService, times(1)).userRegistration(registrationOfUsers);
    }

    @Test
    void getUserById() throws Exception {
        when(userService.getUserById(anyInt())).thenReturn(Optional.of(daoUserWithAllInfo));
        MvcResult result = mockMvc.perform(get("/user/{id}", anyInt()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(userService, times(1)).getUserById(anyInt());
    }

    @Test
    void getAllUser() throws Exception {
        ArrayList<DaoUserWithAllInfo> listOfUsers = new ArrayList<>();
        listOfUsers.add(daoUserWithAllInfo);
        when(userService.getAllUser()).thenReturn(Optional.of(listOfUsers));
        MvcResult result = mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(userService, times(1)).getAllUser();
    }

    @Test
    void updateUserByAdmin() throws Exception {
        when(userService.updateUserByAdmin(any(UpdateUserByAdmin.class))).thenReturn(true);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(new UpdateUserByAdmin());
        MvcResult result = mockMvc.perform(put("/user/updateByAdmin").contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(userService, times(1)).updateUserByAdmin(any(UpdateUserByAdmin.class));
    }

    @Test
    void updateUserByUser() throws Exception {
        when(userService.updateUserByUser(any(UpdateUserByUser.class))).thenReturn(true);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(new UpdateUserByUser());
        MvcResult result = mockMvc.perform(put("/user/update").contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(userService, times(1)).updateUserByUser(any(UpdateUserByUser.class));
    }

    @Test
    void createUser() throws Exception {
        when(userService.createUser(any(CreateUserByAdmin.class))).thenReturn(daoUserWithAllInfo);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(daoUserWithAllInfo);
        MvcResult result = mockMvc.perform(post("/user").contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(userService, times(1)).createUser(createUserByAdmin);
    }

    @Test
    void deleteUser() throws Exception {
        MvcResult result = mockMvc.perform(delete("/user/{id}", anyInt()))
                .andExpect(status().isNoContent())
                .andReturn();
        verify(userService, times(1)).deleteUser(anyInt());
    }

    @Test
    void getCardsOfUserByIdOfUser() throws Exception {
        ArrayList<DaoCard> listOfCards = new ArrayList<>();
        listOfCards.add(daoCard);
        when(userService.getCardsOfUserByIdOfUser(anyInt())).thenReturn(listOfCards);
        MvcResult result = mockMvc.perform(get("/user/allCardsOfUser/{id}", anyInt()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(userService, times(1)).getCardsOfUserByIdOfUser(anyInt());
    }

    @Test
    void getAllInfoAboutTransactionByUserIdForUser() throws Exception {
        ArrayList<TransactionAboutUserById> listOfTransactions = new ArrayList<>();
        listOfTransactions.add(transactionAboutUserById);
        when(transactionService.getAllOfTransactionAboutUserByUserIdForUser(anyInt())).thenReturn(Optional.of(listOfTransactions));
        MvcResult result = mockMvc.perform(get("/user/allTransactionForUser/{id}", anyInt()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(transactionService, times(1)).getAllOfTransactionAboutUserByUserIdForUser(anyInt());
    }

    @Test
    void getAllInfoAboutTransactionByUserIdForAdmin() throws Exception {
        ArrayList<DaoTransactionWithAllInfo> listOfTransactions = new ArrayList<>();
        listOfTransactions.add(daoTransactionWithAllInfo);
        when(userService.getAllOfTransactionAboutUserByIdForAdmin(anyInt())).thenReturn(Optional.of(listOfTransactions));
        MvcResult result = mockMvc.perform(get("/user/allTransactionForAdmin/{id}", anyInt()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(userService, times(1)).getAllOfTransactionAboutUserByIdForAdmin(anyInt());
    }

    @Test
    void putMoneyOnAccountByCard() throws Exception {
        when(userService.putMoneyOnCurrentAmount(1000, cardForUserView)).thenReturn(true);
        MvcResult result = mockMvc.perform(put("/user/putMoney/{sum}", 1000))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        boolean resultEqual = userService.putMoneyOnCurrentAmount(1000, cardForUserView);
        assertTrue(resultEqual);
    }
}