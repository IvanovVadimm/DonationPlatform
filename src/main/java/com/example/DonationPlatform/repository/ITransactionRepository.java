package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ITransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT * FROM user_table WHERE id = :senderId and total_amount_of_transfers >= :amount)", countQuery = "SELECT * FROM user_table WHERE id = :senderId")
    boolean countOnAmountIsMoreThanOnOperation(Integer senderId, Integer amount);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE user_table SET current_amount_on_account = current_amount_on_account-:sum, total_amount_of_transfers = total_amount_of_transfers+:sum  WHERE id = :senderId and current_amount_on_account-:sum >= 0" ,countQuery = "SELECT * FROM user_table WHERE id = :senderId")
    void withdrawalCashFromAmount(int sum, int senderId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE user_table SET current_amount_on_account = current_amount_on_account+:sum WHERE id = :receiverId", countQuery = "SELECT * FROM user_table  WHERE id = :receiverId")
    void gettingMoneyIntoAnAccount(int sum, int receiverId);
}
