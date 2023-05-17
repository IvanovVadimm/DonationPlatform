package com.example.DonationPlatform.exceptions.usersExceptions;

public class UserIsAlreadyExistInDataBaseWithNickNameException extends Exception{

    private String nickName;

    public UserIsAlreadyExistInDataBaseWithNickNameException(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "User is already exist with login "+nickName;
    }
}