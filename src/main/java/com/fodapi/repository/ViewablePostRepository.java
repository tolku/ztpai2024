package com.fodapi.repository;

import com.fodapi.dto.ViewablePostDTO;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ViewablePostRepository {

    @Autowired
    EntityManager entityManager;

    public List<ViewablePostDTO> retrieveViewablePostWithComments(String postId) {
        try {
             return entityManager.createNativeQuery("SELECT r.topic, r.post_content, r.image_path, r.likes_count, c.content" +
                             " AS comment_content, c.user_id_fk " +
                             "AS comment_author, c.creation_date " +
                             "AS comment_creation_date, r.creation_date, r.post_id, r.user_id " +
                             "FROM comments c " +
                             "RIGHT JOIN ( SELECT re.topic " +
                             "AS topic, pc.content " +
                             "AS post_content, pc.image_path " +
                             "AS image_path, re.likes_count " +
                             "AS likes_count, re.creation_date " +
                             "AS creation_date, re.post_id " +
                             "AS post_id, re.user_id " +
                             "AS user_id " +
                             "FROM post_contents pc " +
                             "RIGHT JOIN ( SELECT res.topic " +
                             "AS topic, COUNT(l.post_id_fk) " +
                             "AS likes_count, res.creation_date " +
                             "AS creation_date, l.post_id_fk " +
                             "AS post_id, res.user_id_fk" +
                             " AS user_id" +
                             " FROM likes l RIGHT JOIN ( SELECT * " +
                             "FROM post_titles pt " +
                             "WHERE pt.id = :post_id ) res " +
                             "ON l.post_id_fk = res.id" +
                             " GROUP BY l.post_id_fk, res.topic, res.creation_date, res.user_id_fk ) re " +
                             "ON pc.post_title_id_fk = re.post_id ) r " +
                             "ON c.post_title_fk = r.post_id", "ViewablePostDTOMapping")
                     .setParameter("post_id", postId)
                     .getResultList();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
