package com.fodapi.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "post_titles", schema = "public", catalog = "projektztpai")
public class PostTitlesEntity {
    @Basic
    @Column(name = "topic")
    private String topic;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "creation_date")
    private Timestamp creationDate;
    @Basic
    @Column(name = "user_id_fk")
    private String userIdFk;
    @OneToMany(mappedBy = "id")
    private Collection<CommentsEntity> commentsById;
    @OneToMany(mappedBy = "id")
    private Collection<LikesEntity> likesById;
    @OneToMany(mappedBy = "id")
    private Collection<PostContentsEntity> postContentsById;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserIdFk() {
        return userIdFk;
    }

    public void setUserIdFk(String userIdFk) {
        this.userIdFk = userIdFk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostTitlesEntity that = (PostTitlesEntity) o;
        return Objects.equals(topic, that.topic) && Objects.equals(id, that.id) && Objects.equals(creationDate, that.creationDate) && Objects.equals(userIdFk, that.userIdFk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, id, creationDate, userIdFk);
    }

    public Collection<CommentsEntity> getCommentsById() {
        return commentsById;
    }

    public void setCommentsById(Collection<CommentsEntity> commentsById) {
        this.commentsById = commentsById;
    }

    public Collection<LikesEntity> getLikesById() {
        return likesById;
    }

    public void setLikesById(Collection<LikesEntity> likesById) {
        this.likesById = likesById;
    }

    public Collection<PostContentsEntity> getPostContentsById() {
        return postContentsById;
    }

    public void setPostContentsById(Collection<PostContentsEntity> postContentsById) {
        this.postContentsById = postContentsById;
    }
}
