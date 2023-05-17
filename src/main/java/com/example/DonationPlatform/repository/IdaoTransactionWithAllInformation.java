package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.daotransaction.DaoTransactionWithAllInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface IdaoTransactionWithAllInformation extends JpaRepository<DaoTransactionWithAllInfo, Integer> {

    ArrayList<DaoTransactionWithAllInfo> findAllBySenderId(int id);
}