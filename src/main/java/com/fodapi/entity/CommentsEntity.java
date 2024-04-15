package com.fodapi;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "comments", schema = "public", catalog = "projektztpai")
public class CommentsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "creation_date")
    private Timestamp creationDate;
    @Basic
    @Column(name = "user_id_fk")
    private String userIdFk;
    @Basic
    @Column(name = "post_title_fk")
    private String postTitleFk;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getPostTitleFk() {
        return postTitleFk;
    }

    public void setPostTitleFk(String postTitleFk) {
        this.postTitleFk = postTitleFk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentsEntity that = (CommentsEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(content, that.content) && Objects.equals(creationDate, that.creationDate) && Objects.equals(userIdFk, that.userIdFk) && Objects.equals(postTitleFk, that.postTitleFk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, creationDate, userIdFk, postTitleFk);
    }
}
