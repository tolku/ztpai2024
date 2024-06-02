package com.fodapi.dto;

import java.sql.Timestamp;

public class ViewablePostDTO {
    private String topic;
    private String postContent;
    private String imagePath;
    private String likesCount;
    private String commentContent;
    private String commentAuthor;
    private Timestamp commentCreationDate;
    private Timestamp postCreationDate;
    private String postId;
    private String userId;

    public ViewablePostDTO(String topic, String postContent, String imagePath,
                           String likesCount, String commentContent, String commentAuthor,
                           Timestamp commentCreationDate, Timestamp postCreationDate,
                           String postId, String userId) {
        this.topic = topic;
        this.postContent = postContent;
        this.imagePath = imagePath;
        this.likesCount = likesCount;
        this.commentContent = commentContent;
        this.commentAuthor = commentAuthor;
        this.commentCreationDate = commentCreationDate;
        this.postCreationDate = postCreationDate;
        this.postId = postId;
        this.userId = userId;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setLikesCount(String likesCount) {
        this.likesCount = likesCount;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public void setCommentAuthor(String commentAuthor) {
        this.commentAuthor = commentAuthor;
    }

    public void setCommentCreationDate(Timestamp commentCreationDate) {
        this.commentCreationDate = commentCreationDate;
    }

    public void setPostCreationDate(Timestamp postCreationDate) {
        this.postCreationDate = postCreationDate;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTopic() {
        return topic;
    }

    public String getPostContent() {
        return postContent;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getLikesCount() {
        return likesCount;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public Timestamp getCommentCreationDate() {
        return commentCreationDate;
    }

    public Timestamp getPostCreationDate() {
        return postCreationDate;
    }

    public String getPostId() {
        return postId;
    }

    public String getUserId() {
        return userId;
    }
}
