package com.example.DonationPlatform.utils;

import com.example.DonationPlatform.domain.Card;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardMapper implements RowMapper<Card> {
    @Override
    public Card mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Card card = new Card();

        card.setNumberOfCard(resultSet.getString("card_number"));
        card.setExpireDate(java.sql.Date.valueOf(String.valueOf(resultSet.getDate("expire_date"))));

        return card;
    }
}