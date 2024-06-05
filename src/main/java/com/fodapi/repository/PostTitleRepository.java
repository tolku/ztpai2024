package com.fodapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.UUID;

@Repository
public class PostTitleRepository {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public void createPostTitle(String postTitle, String userUuid, Timestamp date) {
        try {
            entityManager.createNativeQuery("INSERT INTO post_titles (topic, creation_date, user_id_fk, id) VALUES (?, ?, ?, ?)")
                    .setParameter(1, postTitle)
                    .setParameter(2, date)
                    .setParameter(3, userUuid)
                    .setParameter(4, UUID.randomUUID())
                    .executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Transactional
    public void updatePostTitle(String postTitle, String postId) {
        try {
            entityManager.createNativeQuery("UPDATE post_titles SET topic = :postTitle WHERE id = :postId")
                    .setParameter("postTitle", postTitle)
                    .setParameter("postId", postId)
                    .executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Transactional
    public String retrievePostId(String uuid, String topic, Timestamp dateOfCreation) {
        try {
            return (String) entityManager.createNativeQuery("SELECT id FROM post_titles WHERE user_id_fk = :uuid AND topic = :topic AND creation_date = :date", String.class)
                    .setParameter("uuid", uuid)
                    .setParameter("topic", topic)
                    .setParameter("date", dateOfCreation)
                    .getSingleResult();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public String retrieveUserIdByPostTitleId(String id) {
        try {
            return (String) entityManager.createNativeQuery("SELECT user_id_fk FROM post_titles WHERE id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Transactional
    public void removePostById(String postId) {
        try {
            entityManager.createNativeQuery("DELETE FROM post_titles WHERE id = :postId")
                    .setParameter("postId", postId)
                    .executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
