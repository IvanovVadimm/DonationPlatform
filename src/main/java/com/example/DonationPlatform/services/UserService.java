package com.example.DonationPlatform.services;

import com.example.DonationPlatform.domain.User;
import com.example.DonationPlatform.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(new User());
    }

    public ArrayList<User> getAllUser(){
        return (ArrayList<User>) userRepository.findAll();
    }

    public User updateUser(User user){
        return userRepository.saveAndFlush(user);
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public boolean deleteUser(int id){
        userRepository.deleteById(id);
        return true;
    }
}