package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE user_table SET deleted_account = true WHERE id = :id", countQuery = "SELECT * FROM user_table u WHERE u.id = :id")
    void deleteById(int id);

    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT * FROM user_table u WHERE u.deleted_account = true and u.id = :id)")
    boolean isDeletedUserInDataBaseByIdUserChecked(int id);

    boolean existsUserByLoginOrEmailOrNickName(String login, String email, String nickName);

    boolean existsById(int id);
}
