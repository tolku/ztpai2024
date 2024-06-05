package com.fodapi.repository;

import com.fodapi.entity.UsersEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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

    public void updateUserPassword(String email, String newHashedPassword) {
        try {
            entityManager.createNativeQuery("UPDATE users SET password = :newHashedPassword WHERE email = :email ")
                    .setParameter("newHashedPassword", newHashedPassword)
                    .setParameter("email", email)
                    .executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
