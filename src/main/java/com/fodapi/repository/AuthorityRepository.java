package com.fodapi.repository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class AuthorityRepository {

    @Autowired
    EntityManager entityManager;

    public void addWriteAuthorityToUser(String email) {
        try {
            entityManager.createNativeQuery("INSERT INTO authorities (username, authority, id) VALUES (?, ?, ?)")
                    .setParameter(1, email)
                    .setParameter(2, "WRITE_AUTHORITY")
                    .setParameter(3, UUID.randomUUID())
                    .getResultList();
        } catch (Exception exception) {
            //:P
        }
    }
}
