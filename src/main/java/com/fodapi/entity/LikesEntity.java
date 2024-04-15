package com.fodapi;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "likes", schema = "public", catalog = "projektztpai")
public class LikesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "post_id_fk")
    private String postIdFk;
    @Basic
    @Column(name = "user_id_fk")
    private String userIdFk;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostIdFk() {
        return postIdFk;
    }

    public void setPostIdFk(String postIdFk) {
        this.postIdFk = postIdFk;
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
        LikesEntity that = (LikesEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(postIdFk, that.postIdFk) && Objects.equals(userIdFk, that.userIdFk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postIdFk, userIdFk);
    }
}
