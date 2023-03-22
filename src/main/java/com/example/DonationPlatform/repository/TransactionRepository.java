package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.*;

@Repository
public class TransactionRepository {

    public boolean createTransaction(@RequestBody Transaction transaction) {
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
        return result == 1;
    }
}

