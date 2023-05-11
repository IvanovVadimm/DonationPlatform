package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.CardForUsersView;
import com.example.DonationPlatform.domain.DAOUser.DAOUserWithAllInfo;
import com.example.DonationPlatform.domain.Roles;
import com.example.DonationPlatform.domain.request.RegistrationOfUsers;
import com.example.DonationPlatform.repository.IDAOCardRepository;
import com.example.DonationPlatform.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private IUserRepository userRepository;

    private IDAOCardRepository iCardRepository;

    @Autowired
    public UserService(IUserRepository userRepository, IDAOCardRepository iCardRepository) {
        this.userRepository = userRepository;
        this.iCardRepository = iCardRepository;
    }

    public Optional<DAOUserWithAllInfo> getUserById(int id) {
        Optional<DAOUserWithAllInfo> userById = userRepository.findById(id);
        return userById;
    }

    public Optional<List<DAOUserWithAllInfo>> getAllUser() {
        Optional<List<DAOUserWithAllInfo>> listOfUsers = Optional.ofNullable(userRepository.findAll());
        return listOfUsers;
    }

    public DAOUserWithAllInfo updateUser(DAOUserWithAllInfo user) {
        return userRepository.saveAndFlush(user);
    }

    public DAOUserWithAllInfo createUser(DAOUserWithAllInfo user) {
        if (userRepository.existsUserByLoginOrEmailOrNickName(user.getLogin(), user.getEmail(), user.getNickName())) {
            return new DAOUserWithAllInfo();
        }
        user.setDateOfCreateAccount(new Date(new java.util.Date().getTime()));
        return userRepository.save(user);
    }

    public boolean userIsDeleted(int id) {
        if (userRepository.isDeletedUserInDataBaseByIdUserChecked(id)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean userRegistration(RegistrationOfUsers registrationOfUsers) {
        DAOUserWithAllInfo user = new DAOUserWithAllInfo();
        user.setEmail(registrationOfUsers.getEmail());
        user.setLogin(registrationOfUsers.getLogin());
        user.setPassword(registrationOfUsers.getPassword()); // закодировать когда секурити использовать буду user.setPassword(passwordEncoder.encode(registrationOfUsers.getPassword()));
        user.setBirthdate(registrationOfUsers.getBirthdate());
        user.setNickName(registrationOfUsers.getNickName());

        user.setDateOfCreateAccount(new Date(new java.util.Date().getTime()));
        user.setTotalAmountOfTransfers(0);
        user.setRatingOfUsers("0");
        user.setDeleteOfAccount(false);
        user.setRole(String.valueOf(Roles.USER));

        Optional<DAOUserWithAllInfo> userSaved = Optional.ofNullable(userRepository.save(user));

        return userSaved.isPresent();
    }

    public boolean deleteUser(int id) {
        if (!userIsDeleted(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean putMoneyOnCurrentAmount(int sum, int userId, CardForUsersView card) {

        String cardNumber = card.getNumberOfCard();
        int cardId = card.getId();

        if (userRepository.cardOwnershipCheck(userId, cardNumber)) {
            if (!iCardRepository.isDeletedCardInDataBaseByIdCardsChecked(cardId)) {
                userRepository.putMoneyOnCurrentAmount(sum, userId);
                return true;
            } else {
                ;
            }
        }
        return false;
    }
}
//TODO проверить когда подключу спрингсекурити
   /* public boolean checkingUserRights(int id) throws NoRightToPerformActions {
        int authenticatedUserId = SecurityContextHolder.getContext().getAuthentication().getId();
        if(authenticatedUserId == id ){
            return true;
        } else {
            throw new NoRightToPerformActions();
            return false;
        }*/
