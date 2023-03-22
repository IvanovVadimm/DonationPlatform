package com.example.DonationPlatform.utils;

import com.example.DonationPlatform.domain.User;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login_of_user"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password_of_user"));
        user.setBirthdate(Date.valueOf(resultSet.getString("birthdate")));
        user.setDateOfCreateAccount(Date.valueOf(resultSet.getString("date_of_create_account")));
        user.setDeleteOfAccount(resultSet.getBoolean("deleted_account"));
        user.setTotalAmountOfTransfers(Integer.parseInt(resultSet.getString("total_amount_of_transfers")));
        user.setNickName(resultSet.getString("nickname"));
        user.setRatingOfUsers(resultSet.getString("rating_of_user"));
        return user;
    }
}
