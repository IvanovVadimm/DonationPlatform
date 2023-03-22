package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.User;
import com.example.DonationPlatform.utils.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.*;

@Repository
public class UserRepository {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public User getUserById(int id) {
       return jdbcTemplate.queryForObject("SELECT * FROM user_table WHERE id = ? ",new UserMapper(),id);
    }


    public boolean createUser(User user) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/donation_platform", "postgres", "root")) {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO user_table (id, email, login_of_user, password_of_user, birthdate, date_of_create_account, deleted_account, total_amount_of_transfers,nickname, rating_of_user) " +
                    "VALUES (DEFAULT, ?, ?, ?, ?, ?, DEFAULT, DEFAULT, ?, DEFAULT)");
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setDate(4, user.getBirthdate()); //передаём в формате YYYY-MM-DD
            statement.setDate(5, new Date((new java.util.Date()).getTime()));
            statement.setString(6, user.getNickName());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("something wrong...." + e.getMessage());
        }
        return result == 1;
    }

    public boolean updateUser(int id, String email, String login, String nickname, String password) {
        int result = 0;

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/donation_platform", "postgres", "root")) {

            PreparedStatement statement = connection.prepareStatement("UPDATE user_table SET email = ?, login_of_user = ?, nickname= ?, password_of_user= ? WHERE id =?");
            statement.setString(1, email);
            statement.setString(2, login);
            statement.setString(3, nickname);
            statement.setString(4, password);
            statement.setInt(5, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("something wrong...." + e.getMessage());
        }
        return result == 1;
    }
}
