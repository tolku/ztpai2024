package com.fodapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class CommentRepository {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public void addComment(String commentContent, String postId, String userId) {
        try {
            entityManager.createNativeQuery("INSERT INTO comments (id, content, creation_date, user_id_fk, post_title_fk) VALUES (?, ?, ?, ?, ?)")
                    .setParameter(1, UUID.randomUUID())
                    .setParameter(2, commentContent)
                    .setParameter(3, Timestamp.valueOf(LocalDateTime.now()))
                    .setParameter(4, userId)
                    .setParameter(5, postId)
                    .executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
