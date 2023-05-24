package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.create.CardForUserView;
import com.example.DonationPlatform.domain.create.CreateUserByAdmin;
import com.example.DonationPlatform.domain.daocard.DaoCard;
import com.example.DonationPlatform.domain.daotransaction.DaoTransactionWithAllInfo;
import com.example.DonationPlatform.domain.daouser.DaoUserWithAllInfo;
import com.example.DonationPlatform.domain.enums.Roles;
import com.example.DonationPlatform.domain.request.RegistrationOfUsers;
import com.example.DonationPlatform.domain.update.UpdateUserByAdmin;
import com.example.DonationPlatform.domain.update.UpdateUserByUser;
import com.example.DonationPlatform.exceptions.cardsExceptions.AttemptToReplenishTheAccountWithANonExistedCardException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardExpiredException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardNotFoundExceptionByCardNumberException;
import com.example.DonationPlatform.exceptions.cardsExceptions.CardWasDeletedException;
import com.example.DonationPlatform.exceptions.usersExceptions.NoRightToPerformActionsException;
import com.example.DonationPlatform.exceptions.usersExceptions.NotFoundUserInDataBaseByIdException;
import com.example.DonationPlatform.exceptions.usersExceptions.UserIsAlreadyExistInDataBaseWithEmailException;
import com.example.DonationPlatform.exceptions.usersExceptions.UserIsAlreadyExistInDataBaseWithLoginException;
import com.example.DonationPlatform.exceptions.usersExceptions.UserIsAlreadyExistInDataBaseWithNickNameException;
import com.example.DonationPlatform.repository.IdaoCardRepository;
import com.example.DonationPlatform.repository.IdaoTransactionWithAllInformation;
import com.example.DonationPlatform.repository.IuserRepository;
import com.example.DonationPlatform.utils.ReturnLoginSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    final String adminRole = String.valueOf(Roles.ADMIN);
    private final IuserRepository userRepository;
    private final IdaoCardRepository cardRepository;
    private final CardService cardService;
    private final IdaoTransactionWithAllInformation iDaoTransactionRepositoryWithAllInformation;
    private final PasswordEncoder passwordEncoder;
    private final ReturnLoginSecurityUser returnLoginSecurityUser;

    @Autowired
    public UserService(IuserRepository userRepository, IdaoCardRepository iCardRepository, CardService cardService, IdaoTransactionWithAllInformation iDaoTransactionRepositoryWithAllInformation, PasswordEncoder passwordEncoder, ReturnLoginSecurityUser returnLoginSecurityUser) {
        this.userRepository = userRepository;
        this.cardRepository = iCardRepository;
        this.cardService = cardService;
        this.iDaoTransactionRepositoryWithAllInformation = iDaoTransactionRepositoryWithAllInformation;
        this.passwordEncoder = passwordEncoder;
        this.returnLoginSecurityUser = returnLoginSecurityUser;
    }

    public Optional<DaoUserWithAllInfo> getUserById(int id) throws NotFoundUserInDataBaseByIdException, NoRightToPerformActionsException {
        String loginSecurityUser = returnLoginSecurityUser.getLoginOfSecurityUser();
        DaoUserWithAllInfo securityUser = userRepository.findByLogin(loginSecurityUser);
        int idSecurityUser = securityUser.getId();
        String roleSecurityUser = securityUser.getRole();
        if ((idSecurityUser == id) || (roleSecurityUser.equals(adminRole))) {
            Optional<DaoUserWithAllInfo> userById = userRepository.findById(id);
            if (userById.isEmpty()) {
                throw new NotFoundUserInDataBaseByIdException(id);
            }
            return userById;
        } else {
            throw new NoRightToPerformActionsException();
        }
    }

    public Optional<List<DaoUserWithAllInfo>> getAllUser() {
        return Optional.of(userRepository.findAll());
    }

    @Transactional
    public boolean updateUserByAdmin(UpdateUserByAdmin user) throws NotFoundUserInDataBaseByIdException, UserIsAlreadyExistInDataBaseWithEmailException, UserIsAlreadyExistInDataBaseWithNickNameException, UserIsAlreadyExistInDataBaseWithLoginException {
        DaoUserWithAllInfo userFromDataBase = userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundUserInDataBaseByIdException(user.getId()));
        DaoUserWithAllInfo updaterUserByAdmin = new DaoUserWithAllInfo();
        updaterUserByAdmin.setId(user.getId());
        if (!userRepository.existsUserByEmail(user.getEmail())) {
            updaterUserByAdmin.setEmail(user.getEmail());
        } else if (user.getEmail().equals(userFromDataBase.getEmail())) {
            updaterUserByAdmin.setEmail(userFromDataBase.getEmail());
        } else {
            throw new UserIsAlreadyExistInDataBaseWithEmailException(user.getEmail());
        }
        if (!userRepository.existsUserByNickName(user.getNickName())) {
            updaterUserByAdmin.setNickName(user.getNickName());
        } else if (user.getNickName().equals(userFromDataBase.getNickName())) {
            updaterUserByAdmin.setNickName(userFromDataBase.getNickName());
        } else {
            throw new UserIsAlreadyExistInDataBaseWithNickNameException(user.getNickName());
        }
        if (!userRepository.existsUserByLogin(user.getLogin())) {
            updaterUserByAdmin.setLogin(user.getLogin());
        } else if (user.getLogin().equals(userFromDataBase.getLogin())) {
            updaterUserByAdmin.setLogin(userFromDataBase.getLogin());
        } else {
            throw new UserIsAlreadyExistInDataBaseWithLoginException(user.getLogin());
        }
        updaterUserByAdmin.setCurrentAmountOnAccount(userFromDataBase.getCurrentAmountOnAccount());
        updaterUserByAdmin.setDateOfCreateAccount(userFromDataBase.getDateOfCreateAccount());
        updaterUserByAdmin.setTotalAmountOfTransfers(userFromDataBase.getTotalAmountOfTransfers());
        updaterUserByAdmin.setDeleteOfAccount(userFromDataBase.getDeleteOfAccount());
        updaterUserByAdmin.setBirthdate(userFromDataBase.getBirthdate());
        if (!(user.getPassword() == null)) {
            updaterUserByAdmin.setPassword(user.getPassword());
        } else {
            updaterUserByAdmin.setPassword(userFromDataBase.getPassword());
        }
        if (!(user.getRole() == null)) {
            updaterUserByAdmin.setRole(user.getRole());
        } else {
            updaterUserByAdmin.setRole(userFromDataBase.getRole());
        }
        if (!(user.getRatingOfUsers() == null)) {
            updaterUserByAdmin.setRatingOfUsers(user.getRatingOfUsers());
        } else {
            updaterUserByAdmin.setRatingOfUsers(userFromDataBase.getRatingOfUsers());
        }
        userRepository.save(updaterUserByAdmin);
        return true;
    }

    @Transactional
    public boolean updateUserByUser(UpdateUserByUser user) throws NoRightToPerformActionsException, UserIsAlreadyExistInDataBaseWithLoginException, UserIsAlreadyExistInDataBaseWithNickNameException, UserIsAlreadyExistInDataBaseWithEmailException, NotFoundUserInDataBaseByIdException {
        String loginSecurityUser = returnLoginSecurityUser.getLoginOfSecurityUser();
        int idSecurityUser = userRepository.findByLogin(loginSecurityUser).getId();
        DaoUserWithAllInfo userFromDataBase = userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundUserInDataBaseByIdException(user.getId()));
        DaoUserWithAllInfo updatedUserByUser = new DaoUserWithAllInfo();
        updatedUserByUser.setId(user.getId());
        if (idSecurityUser == user.getId()) {
            if (!userRepository.existsUserByEmail(user.getEmail())) {
                updatedUserByUser.setEmail(user.getEmail());
            } else if (user.getEmail().equals(userFromDataBase.getEmail())) {
                updatedUserByUser.setEmail(userFromDataBase.getEmail());
            } else {
                throw new UserIsAlreadyExistInDataBaseWithEmailException(user.getEmail());
            }
            if (!userRepository.existsUserByNickName(user.getNickName())) {
                updatedUserByUser.setNickName(user.getNickName());
            } else if (user.getNickName().equals(userFromDataBase.getNickName())) {
                updatedUserByUser.setNickName(userFromDataBase.getNickName());
            } else {
                throw new UserIsAlreadyExistInDataBaseWithNickNameException(user.getNickName());
            }
            if (!userRepository.existsUserByLogin(user.getLogin())) {
                updatedUserByUser.setLogin(user.getLogin());
            } else if (user.getLogin().equals(userFromDataBase.getLogin())) {
                updatedUserByUser.setLogin(userFromDataBase.getLogin());
            } else {
                throw new UserIsAlreadyExistInDataBaseWithLoginException(user.getLogin());
            }
            updatedUserByUser.setCurrentAmountOnAccount(userFromDataBase.getCurrentAmountOnAccount());
            updatedUserByUser.setDateOfCreateAccount(userFromDataBase.getDateOfCreateAccount());
            updatedUserByUser.setTotalAmountOfTransfers(userFromDataBase.getTotalAmountOfTransfers());
            updatedUserByUser.setDeleteOfAccount(userFromDataBase.getDeleteOfAccount());
            updatedUserByUser.setRole(userFromDataBase.getRole());
            updatedUserByUser.setRatingOfUsers(userFromDataBase.getRatingOfUsers());
            if (!(user.getPassword() == null)) {
                updatedUserByUser.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                updatedUserByUser.setPassword(userFromDataBase.getPassword());
            }
            if (!(user.getBirthdate() == null)) {
                updatedUserByUser.setBirthdate(user.getBirthdate());
            } else {
                updatedUserByUser.setBirthdate(userFromDataBase.getBirthdate());
            }
            userRepository.save(updatedUserByUser);
            return true;
        } else {
            throw new NoRightToPerformActionsException();
        }
    }

    public DaoUserWithAllInfo createUser(CreateUserByAdmin user) throws UserIsAlreadyExistInDataBaseWithEmailException, UserIsAlreadyExistInDataBaseWithNickNameException, UserIsAlreadyExistInDataBaseWithLoginException {
        String emailOfUser = user.getEmail();
        String nickNameOfUser = user.getNickName();
        String loginOfUser = user.getLogin();
        if (userRepository.existsUserByEmail(emailOfUser)) {
            throw new UserIsAlreadyExistInDataBaseWithEmailException(emailOfUser);
        } else if (userRepository.existsUserByNickName(nickNameOfUser)) {
            throw new UserIsAlreadyExistInDataBaseWithNickNameException(nickNameOfUser);
        } else if (userRepository.existsUserByLogin(loginOfUser)) {
            throw new UserIsAlreadyExistInDataBaseWithLoginException(loginOfUser);
        }
        DaoUserWithAllInfo userIntoDataBase = new DaoUserWithAllInfo();
        userIntoDataBase.setEmail(user.getEmail());
        userIntoDataBase.setPassword(passwordEncoder.encode(user.getPassword()));
        userIntoDataBase.setLogin(user.getLogin());
        userIntoDataBase.setNickName(user.getNickName());
        userIntoDataBase.setBirthdate(user.getBirthdate());
        userIntoDataBase.setDateOfCreateAccount(user.getDateOfCreateAccount());
        userIntoDataBase.setTotalAmountOfTransfers(user.getTotalAmountOfTransfers());
        userIntoDataBase.setCurrentAmountOnAccount(user.getCurrentAmountOnAccount());
        userIntoDataBase.setRatingOfUsers(user.getRatingOfUsers());
        userIntoDataBase.setDeleteOfAccount(user.isDeleteOfAccount());
        userIntoDataBase.setRole(user.getRole());
        return userRepository.save(userIntoDataBase);
    }

    public boolean userIsDeleted(int id) {
        return userRepository.isDeletedUserInDataBaseByIdUserChecked(id);
    }

    @Transactional
    public boolean userRegistration(RegistrationOfUsers registrationOfUsers) throws UserIsAlreadyExistInDataBaseWithLoginException, UserIsAlreadyExistInDataBaseWithEmailException, UserIsAlreadyExistInDataBaseWithNickNameException {
        String login = registrationOfUsers.getLogin();
        String email = registrationOfUsers.getEmail();
        String nickName = registrationOfUsers.getNickName();
        boolean userWithSameLoginExistInDataBase = userRepository.existsUserByLogin(login);
        boolean userWithSameEmailExistInDataBase = userRepository.existsUserByEmail(email);
        boolean userWithSameNickNameExistInDataBase = userRepository.existsUserByNickName(nickName);
        if (userWithSameLoginExistInDataBase) {
            throw new UserIsAlreadyExistInDataBaseWithLoginException(login);
        } else if (userWithSameEmailExistInDataBase) {
            throw new UserIsAlreadyExistInDataBaseWithEmailException(email);
        } else if (userWithSameNickNameExistInDataBase) {
            throw new UserIsAlreadyExistInDataBaseWithNickNameException(nickName);
        }
        DaoUserWithAllInfo user = new DaoUserWithAllInfo();
        user.setEmail(registrationOfUsers.getEmail());
        user.setLogin(registrationOfUsers.getLogin());
        user.setPassword(passwordEncoder.encode(registrationOfUsers.getPassword()));
        user.setBirthdate(registrationOfUsers.getBirthdate());
        user.setNickName(registrationOfUsers.getNickName());
        user.setDateOfCreateAccount(new Date(new java.util.Date().getTime()));
        user.setTotalAmountOfTransfers(0);
        user.setCurrentAmountOnAccount(0);
        user.setRatingOfUsers("firstLevel");
        user.setDeleteOfAccount(false);
        user.setRole(String.valueOf(Roles.USER));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(int id) throws NoRightToPerformActionsException {
        String userLogin = returnLoginSecurityUser.getLoginOfSecurityUser();
        DaoUserWithAllInfo securityUser = userRepository.findByLogin(userLogin);
        int securityUserId = securityUser.getId();
        String securityUserRole = securityUser.getRole();
        if (securityUserId == id || securityUserRole.equals(adminRole)) {
            if (!userIsDeleted(id)) {
                userRepository.deleteById(id);
                return true;
            }
        }
        throw new NoRightToPerformActionsException();
    }

    @Transactional
    public boolean putMoneyOnCurrentAmount(int sum, CardForUserView card) throws AttemptToReplenishTheAccountWithANonExistedCardException, CardWasDeletedException, CardNotFoundExceptionByCardNumberException, CardExpiredException {
        String loginSecurityUser = returnLoginSecurityUser.getLoginOfSecurityUser();
        DaoUserWithAllInfo userFromDataBase = userRepository.findByLogin(loginSecurityUser);
        int userId = userFromDataBase.getId();
        String cardNumber = card.getNumberOfCard();
        boolean userIsOwnerThisCard = userRepository.cardOwnershipCheck(userId, cardNumber);
        boolean cardWasNotDeleted = cardRepository.isDeletedCardInDataBaseByNumberCardCardsChecked(cardNumber);
        if (cardRepository.existsCardByNumberOfCard(card.getNumberOfCard())) {
            if (userIsOwnerThisCard) {
                if (!cardWasNotDeleted) {
                    if (!cardService.cardIsExpired(card)) {
                        userRepository.putMoneyOnCurrentAmount(sum, userId);
                        return true;
                    } else {
                        throw new CardExpiredException(card.getNumberOfCard());
                    }
                } else {
                    throw new CardWasDeletedException(cardNumber);
                }
            } else {
                throw new AttemptToReplenishTheAccountWithANonExistedCardException(userId);
            }
        } else {
            throw new CardNotFoundExceptionByCardNumberException(cardNumber);
        }
    }

    public ArrayList<DaoCard> getCardsOfUserByIdOfUser(int id) throws NoRightToPerformActionsException {
        String loginSecurityUser = returnLoginSecurityUser.getLoginOfSecurityUser();
        DaoUserWithAllInfo securityUser = userRepository.findByLogin(loginSecurityUser);
        int idSecurityUser = securityUser.getId();
        String idSecurityRole = securityUser.getRole();
        if (idSecurityUser == id || idSecurityRole.equals(adminRole)) {
            return cardRepository.findAllCardByUserId(id);
        } else {
            throw new NoRightToPerformActionsException();
        }
    }

    public Optional<ArrayList<DaoTransactionWithAllInfo>> getAllOfTransactionAboutUserByIdForAdmin(int id) throws NotFoundUserInDataBaseByIdException {
        boolean existUserInDataBase = userRepository.existsById(id);
        if (!existUserInDataBase) {
            throw new NotFoundUserInDataBaseByIdException(id);
        }
        Optional<ArrayList<DaoTransactionWithAllInfo>> listOptional = Optional.ofNullable(iDaoTransactionRepositoryWithAllInformation.findAllBySenderId(id));
        return listOptional;
    }
}