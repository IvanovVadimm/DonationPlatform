package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE user_table SET deleted_account = true WHERE id = :userId;", countQuery = "SELECT * FROM user_table u WHERE u.id = :userId")
    void deleteById(int userId);

    Optional<User> findUserByLogin(String login);
}