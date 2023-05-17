/*
package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.daouser.DaoUserWithAllInfo;
import com.example.DonationPlatform.domain.enums.Roles;
import com.example.DonationPlatform.exceptions.usersExceptions.NoRightToPerformActionsException;
import com.example.DonationPlatform.exceptions.usersExceptions.NotFoundUserInDataBaseByIdException;
import com.example.DonationPlatform.repository.IdaoCardRepository;
import com.example.DonationPlatform.repository.IdaoTransactionWithAllInformation;
import com.example.DonationPlatform.repository.IuserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceTest userServiceTest;

    private UserService userService;

    final String adminRole = String.valueOf(Roles.ADMIN);
    @Mock
    private IuserRepository userRepository;
    @Mock
    private IdaoCardRepository cardRepository;
    @Mock
    private CardService cardService;
    @Mock
    private IdaoTransactionWithAllInformation iDaoTransactionRepositoryWithAllInformation;
    SecurityContextHolder securityContextHolder = new SecurityContextHolder();

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Mock
    String loginSecurityUser;

    int userId;
    private final DaoUserWithAllInfo user = new DaoUserWithAllInfo();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, cardRepository, cardService, iDaoTransactionRepositoryWithAllInformation, passwordEncoder);
        userId = 1;
        user.setId(1);
        loginSecurityUser = securityContextHolder.getContext().getAuthentication().getName();
        user.setEmail("test-email@gmail.com");
        user.setRatingOfUsers("firstLevel");
        user.setNickName("John");
        user.setPassword("121212Hs");
        user.setLogin("80291112233");
        user.setDateOfCreateAccount(Date.valueOf(LocalDate.now()));
        user.setBirthdate(Date.valueOf(LocalDate.now()));
        user.setRole("USER");
        user.setTotalAmountOfTransfers(0);
        user.setCurrentAmountOnAccount(100);
    }


    */
/*@Test
    void getUserById() throws NotFoundUserInDataBaseByIdException, NoRightToPerformActionsException {
        when(userService.getUserById(userId)).getMock().thenReturn(Optional.of(user));
        DaoUserWithAllInfo result = userService.getUserById(userId).orElseThrow();
        assertEquals(user, result);
    }*//*


    @Test
    void getAllUser() {

    }

    @Test
    void updateUserByAdmin() {
    }

    @Test
    void updateUserByUser() {
    }

    @Test
    void createUser() {
    }

    @Test
    void userIsDeleted() throws NotFoundUserInDataBaseByIdException, NoRightToPerformActionsException {

    }

    @Test
    void userRegistration() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void putMoneyOnCurrentAmount() {
    }

    @Test
    void getCardsOfUserByIdOfUser() {
    }

    @Test
    void getAllOfTransactionAboutUserByIdForAdmin() {
    }
}*/
