package com.fodapi.repository;

import com.fodapi.entity.UsersEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;

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

    public void updateUserTokenAndExpDate(String jwt, String milis) {
        try {
            entityManager.createNativeQuery("UPDATE users SET cookie = :token, cookie_expiration_date = :new_date")
                    .setParameter("token", jwt)
                    .setParameter("new_date", new Timestamp(Instant.now().toEpochMilli() + Integer.parseInt(milis)))
                    .executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
