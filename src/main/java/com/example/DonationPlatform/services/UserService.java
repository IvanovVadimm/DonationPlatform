package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.User;
import com.example.DonationPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    public boolean createUser(User user) {
        return userRepository.createUser(user);
    }

    public boolean updateUser(int id, String email, String login, String nickname, String password) {
        return userRepository.updateUser(id, email, login, nickname, password);
    }
}