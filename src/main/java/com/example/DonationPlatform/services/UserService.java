package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.User;
import com.example.DonationPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

    public ArrayList<User> getAllUser(){
        return userRepository.getAllUser();
    }

    public boolean updateUser(User user){
        return userRepository.updateUser(user);
    }
    public boolean createUser(User user) {
        return userRepository.createUser(user);
    }
}