package com.example.DonationPlatform.repository;

import com.example.DonationPlatform.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;

@Repository
public class UserRepository {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final SessionFactory sessionFactory;


    public UserRepository() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public User getUserById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction(); // начало транзакции

        User user = session.get(User.class, id);

        session.getTransaction().commit(); // конец траназакции
        session.close();
        if (user != null) {
            return user;
        }
        return new User();
    }

    public ArrayList<User> getAllUser() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from User");
        ArrayList<User> users = (ArrayList<User>) query.getResultList();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    public boolean deleteUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            user.setDeleteOfAccount(true);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            log.warn(e.getMessage());
            return false;
        }
    }

    public boolean updateUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            log.warn(e.getMessage());
            return false;
        }
    }

    public boolean createUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            log.warn(e.getMessage());
            return false;
        }
    }
}