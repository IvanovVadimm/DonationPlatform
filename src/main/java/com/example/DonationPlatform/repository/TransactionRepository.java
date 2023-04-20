package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository {

    private final SessionFactory sessionFactory;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public TransactionRepository() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public Transaction getTransactionById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Transaction transaction = session.get(Transaction.class, id);
        session.getTransaction().commit();
        session.close();
        if (transaction != null) {
            return transaction;
        } else {
            return new Transaction();
        }
    }

    public boolean createTransaction(Transaction transaction) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(transaction);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            log.warn(e.getMessage());
            return false;
        }
    }
}

/*
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/donation_platform", "postgres", "root")) {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO transaction_table ( date_of_transaction, amount_of_transfer, receiver_id, sender_id) " +
                    "VALUES (?, ?, ?, ?)");
            statement.setDate(1, new Date(new java.util.Date().getTime()));
            statement.setInt(2, transaction.getAmountOfTransfer());
            statement.setInt(3, transaction.getReceiverId());
            statement.setInt(4, transaction.getSenderId());
            result = statement.executeUpdate();


        } catch (SQLException e) {
            System.out.println("something wrong...." + e.getMessage());
        }
        return result == 1;*//*

    }
}*/