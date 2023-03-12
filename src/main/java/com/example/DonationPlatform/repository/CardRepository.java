package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class CardRepository {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public CardRepository() throws SQLException {
    }

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

    public boolean checkCardExistInDataBase(String numberOfCard) {

        boolean result = false;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/donation_platform", "postgres", "root")) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cards_table WHERE card_number = ?");
            preparedStatement.setString(1,numberOfCard);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = resultSet.next();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        if (result) {
            log.info("Card is in database");
        } else {
            log.info("Card isn't in database");
        }
        return result;
    }

    public boolean createCard(int id, String numberOfCard, Date expireDate) {
        int result = 0;
        if (checkCardExistInDataBase(numberOfCard)) {
            log.info("Card wasn't putted in database");
            return false;
        } else {
            Card card = new Card();

            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/donation_platform", "postgres", "root")) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO card_table (id,card_number,expire_date_of_card)" +
                        "VALUES (DEFAULT,?,?)");
                preparedStatement.setInt(1, id);
                preparedStatement.setString(1, numberOfCard);
                preparedStatement.setDate(1, expireDate);
                result = preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if ((result == 1)) {
                log.info("Card was putted in database");
            } else {
                log.info("Card wasn't putted in database");
            }
            return result == 1;
        }

    }
}
