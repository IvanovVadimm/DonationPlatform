package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.Card;
import com.example.DonationPlatform.domain.User;
import com.example.DonationPlatform.utils.CardMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Repository
public class CardRepository {

    private final SessionFactory sessionFactory;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public CardRepository() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory(); //TODO:to configure class
    }

    public Card getCardById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Card card = session.get(Card.class, id);
        session.getTransaction().commit();
        session.close();
        return card;
    }

    public ArrayList<Card> getCardByUserId(int id) {
        String SQL = "SELECT card_number FROM cards_table INNER JOIN l_cards_of_users on cards_table.id = l_cards_of_users.card_id  WHERE l_cards_of_users.user_id =";
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createNativeQuery(SQL+":userId");
        query.setParameter("userId",id);
        ArrayList<Card> cards = (ArrayList<Card>) query.getResultList();
        session.getTransaction().commit();
        session.close();
        return cards;
    }

    public boolean checkCardExistInDataBase(String numberOfCard) {

        boolean result = false;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/donation_platform", "postgres", "root")) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cards_table WHERE card_number = ?");
            preparedStatement.setString(1, numberOfCard);
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

    public boolean createCard(String numberOfCard, String expireDate) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date docDate;
        try {
            docDate = format.parse(expireDate);
        } catch (ParseException e) {
            log.info("Ошибка преобразования из строки в дату");
            throw new RuntimeException(e);
        }
        int result = 0;
        if (checkCardExistInDataBase(numberOfCard)) {
            log.info("Card wasn't putted in database");
            return false;
        } else {
            Card card = new Card();

            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/donation_platform", "postgres", "root")) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cards_table (card_number,expire_date)" +
                        "VALUES (?,?)");
                preparedStatement.setString(1, numberOfCard);
                preparedStatement.setDate(2, new Date(docDate.getTime()));
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
