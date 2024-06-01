package com.fodapi.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "post_contents", schema = "public", catalog = "projektztpai")
public class PostContentsEntity {
    @Basic
    @Column(name = "image_path")
    private String imagePath;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "post_title_id_fk")
    private String postTitleIdFk;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

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

    public String getPostTitleIdFk() {
        return postTitleIdFk;
    }

    public void setPostTitleIdFk(String postTitleIdFk) {
        this.postTitleIdFk = postTitleIdFk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostContentsEntity that = (PostContentsEntity) o;
        return Objects.equals(imagePath, that.imagePath) && Objects.equals(id, that.id) && Objects.equals(content, that.content) && Objects.equals(postTitleIdFk, that.postTitleIdFk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imagePath, id, content, postTitleIdFk);
    }
}
