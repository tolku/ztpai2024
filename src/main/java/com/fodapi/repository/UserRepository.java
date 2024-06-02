package com.fodapi.repository;

import com.fodapi.entity.UsersEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.UUID;

@Repository
@Transactional
public class UserRepository {

    @Autowired
    EntityManager entityManager;

    public UsersEntity retrieveUserByEmail(String email) {
        try {
            return (UsersEntity) entityManager.createNativeQuery("SELECT * FROM users WHERE email = :email", UsersEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }
    }

    public void updateUserTokenAndExpDate(String email, String jwt, String milis, long currentMilis) {
        try {
            entityManager.createNativeQuery("UPDATE users SET cookie = :token, cookie_expiration_date = :new_date WHERE email = :email")
                    .setParameter("email", email)
                    .setParameter("token", jwt)
                    .setParameter("new_date", new Timestamp(currentMilis + Integer.parseInt(milis)))
                    .executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void createNewUser(String username, String email, String hashedPassword) {
        try {
            entityManager.createNativeQuery("INSERT INTO users (name, email, password, id) VALUES (?, ?, ?, ?)")
                    .setParameter(1, username)
                    .setParameter(2, email)
                    .setParameter(3, hashedPassword)
                    .setParameter(4, UUID.randomUUID())
                    .executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public UsersEntity retrieveUserByToken(String jwt) {
        try {
            return (UsersEntity) entityManager.createNativeQuery("SELECT * FROM users WHERE cookie = :jwt", UsersEntity.class)
                    .setParameter("jwt", jwt)
                    .getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }
    }

    public void updateUserPassword(String jwt, String newHashedPassword) {
        try {
            entityManager.createNativeQuery("UPDATE users SET password = :newHashedPassword WHERE cookie = :jwt ")
                    .setParameter("newHashedPassword", newHashedPassword)
                    .setParameter("jwt", jwt)
                    .executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void removeJwtToken(String jwt) {
        try {
            entityManager.createNativeQuery("UPDATE users SET cookie = :empty WHERE cookie = :jwt")
                    .setParameter("empty", Strings.EMPTY)
                    .setParameter("jwt", jwt)
                    .executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
