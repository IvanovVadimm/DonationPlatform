package com.example.DonationPlatform.repository;


import com.example.DonationPlatform.domain.DAOTransaction.DAOTransactionWithAllInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface IDAOTransactionWithAllInformation extends JpaRepository<DAOTransactionWithAllInfo,Integer> {

ArrayList<DAOTransactionWithAllInfo> findAllBySenderId(int id);
}
