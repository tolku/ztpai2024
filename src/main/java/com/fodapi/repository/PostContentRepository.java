package com.fodapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PostContentRepository {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public void createPostContent(String content, String postTitleUuid, String imagePath) {
        try {
            entityManager.createNativeQuery("INSERT INTO post_contents (image_path, content, post_title_id_fk, id) VALUES (?, ?, ?, ?)")
                    .setParameter(1, imagePath)
                    .setParameter(2, content)
                    .setParameter(3, postTitleUuid)
                    .setParameter(4, UUID.randomUUID())
                    .executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Transactional
    public void updatePostContentById(String postId, String postContent, String imagePath) {
        try {
            entityManager.createNativeQuery("UPDATE post_contents SET content = :content, image_path = :image_path WHERE post_title_id_fk = :postId")
                    .setParameter("content", postContent)
                    .setParameter("image_path", imagePath)
                    .setParameter("postId", postId)
                    .executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
