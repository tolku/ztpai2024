package com.fodapi.dto;

import java.sql.Timestamp;

public class FullPostDTO {

    private String topic;
    private String content;
    private Timestamp creationDate;
    private String postTitleId;
    private String userId;
    private Long likesCount;

    public FullPostDTO(String topic, String content, Timestamp creationDate, String postTitleId, String userId, Long likesCount) {
        this.topic = topic;
        this.content = content;
        this.creationDate = creationDate;
        this.postTitleId = postTitleId;
        this.userId = userId;
        this.likesCount = likesCount;
    }

    public FullPostDTO(String topic, String content, Timestamp creationDate, String postTitleId, String userId) {
        this.topic = topic;
        this.content = content;
        this.creationDate = creationDate;
        this.postTitleId = postTitleId;
        this.userId = userId;
    }

    public String getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public String getPostTitleId() {
        return postTitleId;
    }

    public String getUserId() {
        return userId;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public void setPostTitleId(String postTitleId) {
        this.postTitleId = postTitleId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

}
