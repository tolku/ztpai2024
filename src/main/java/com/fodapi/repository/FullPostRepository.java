package com.fodapi.repository;

import com.fodapi.dto.FullPostDTO;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FullPostRepository {

    @Autowired
    private EntityManager entityManager;

    public List<FullPostDTO> getPostContent() {
        try {
            return entityManager.createNativeQuery("SELECT * FROM ( " +
                            "SELECT r.topic, content, r.creation_date, r.post_title_id, r.user_id, r.count AS likes_count " +
                            "FROM post_contents pc " +
                            "FULL OUTER JOIN " +
                            "(SELECT topic, creation_date, id AS post_title_id, pt.user_id_fk AS user_id, count " +
                            "FROM post_titles pt " +
                            "FULL OUTER JOIN " +
                            "(SELECT post_id_fk, COUNT(post_id_fk) " +
                            "FROM likes " +
                            "GROUP BY post_id_fk) res " +
                            "ON res.post_id_fk = pt.id) r " +
                            "ON pc.post_title_id_fk = r.post_title_id) " +
                            "ORDER BY creation_date DESC", "PostContentDTOMapping")
                    .getResultList();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
