package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.Roles;
import com.example.DonationPlatform.domain.User;
import com.example.DonationPlatform.domain.request.RegistrationOfUsers;
import com.example.DonationPlatform.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    private IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(new User());
    }

    public ArrayList<User> getAllUser() {
        return (ArrayList<User>) userRepository.findAll();
    }

    public User updateUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public User createUser(User user) {
        if (userRepository.existsUserByLoginOrEmailOrNickName(user.getLogin(), user.getEmail(), user.getNickName())) {
            return new User();
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
        User user = new User();
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

        Optional<User> userSaved = Optional.ofNullable(userRepository.save(user));

        return userSaved.isPresent();
    }

    public boolean deleteUser(int id) {
        if (!userIsDeleted(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}