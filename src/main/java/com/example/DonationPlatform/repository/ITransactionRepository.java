package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ITransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT * FROM user_table u WHERE u.id = :senderId and u.total_amount_of_transfers >= :amount)")
    public boolean countOnAmountIsMoreThanOnOperation(Integer senderId, Integer amount);
}
