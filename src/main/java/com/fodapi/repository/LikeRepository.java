package com.fodapi.repository;

import com.fodapi.entity.LikesEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class LikeRepository {

    @Autowired
    EntityManager entityManager;

    public List<LikesEntity> retrieveLikePerPostAndId(String postId, String userId) {
        try {
            return (List<LikesEntity>)entityManager.createNativeQuery("SELECT * FROM likes WHERE post_id_fk = :postId AND user_id_fk = :userId", LikesEntity.class)
                    .setParameter("postId", postId)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (Exception exception) {
            return null;
        }
    }

    @Transactional
    public void createLike(String postId, String userId) {
        try {
            entityManager.createNativeQuery("INSERT INTO likes (id, post_id_fk, user_id_fk) VALUES (?, ?, ?)")
                    .setParameter(1, UUID.randomUUID())
                    .setParameter(2, postId)
                    .setParameter(3, userId)
                    .executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Transactional
    public void removeLike(String postId, String userId) {
        try {
            entityManager.createNativeQuery("DELETE FROM likes WHERE post_id_fk = :postId AND user_id_fk = :userId")
                    .setParameter("postId", postId)
                    .setParameter("userId", userId)
                    .executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
