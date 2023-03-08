package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepository {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public User getUserById(int id) {
        User user = new User();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/donation_platform", "postgres", "root")) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user_table WHERE id = ? ");
            preparedStatement.setInt(id, 1);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }
}
