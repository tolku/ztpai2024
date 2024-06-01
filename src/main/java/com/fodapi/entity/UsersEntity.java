package com.fodapi.entity;

import jakarta.persistence.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "public", catalog = "projektztpai")
public class UsersEntity {
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "cookie")
    private String cookie;
    @Basic
    @Column(name = "cookie_expiration_date")
    private Timestamp cookieExpirationDate;
    @OneToMany(mappedBy = "id")
    private Collection<CommentsEntity> commentsById;
    @OneToMany(mappedBy = "id")
    private Collection<LikesEntity> likesById;
    @OneToMany(mappedBy = "id")
    private Collection<PostTitlesEntity> postTitlesById;
    @OneToMany(mappedBy = "id")
    private Collection<UserRolesEntity> userRolesById;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public Timestamp getCookieExpirationDate() {
        return cookieExpirationDate;
    }

    public void setCookieExpirationDate(Timestamp cookieExpirationDate) {
        this.cookieExpirationDate = cookieExpirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(id, that.id) && Objects.equals(cookie, that.cookie) && Objects.equals(cookieExpirationDate, that.cookieExpirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password, id, cookie, cookieExpirationDate);
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

    public Collection<PostTitlesEntity> getPostTitlesById() {
        return postTitlesById;
    }

    public void setPostTitlesById(Collection<PostTitlesEntity> postTitlesById) {
        this.postTitlesById = postTitlesById;
    }

    public Collection<UserRolesEntity> getUserRolesById() {
        return userRolesById;
    }

    public void setUserRolesById(Collection<UserRolesEntity> userRolesById) {
        this.userRolesById = userRolesById;
    }
}
