package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.Card;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class CardRepository {

    public ArrayList<String> getCardByUserId(int id) {

        ArrayList<String> cardsOfUsers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/donation_platform", "postgres", "root")) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT card_number FROM cards_table INNER JOIN l_cards_of_users on cards_table.id = l_cards_of_users.card_id  WHERE l_cards_of_users.users_id = ? ");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cardsOfUsers.add(resultSet.getString("card_number"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cardsOfUsers;
    }
}