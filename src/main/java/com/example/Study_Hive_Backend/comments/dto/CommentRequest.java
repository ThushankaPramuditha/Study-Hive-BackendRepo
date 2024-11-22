// CommentRequest.java
package com.example.Study_Hive_Backend.comments.dto;

public class CommentRequest {
    private String content;
    private String authorEmail;// Store only the email in the request

    private Integer upvote = 0;

    private Integer downvote = 0;

    // Getters and Setters
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getAuthorEmail() { return authorEmail; }
    public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }

    public Integer getUpvote() { return upvote; }
    public void setUpvote(Integer upvote) { this.upvote = upvote; }

    public Integer getDownvote() { return downvote; }
    public void setDownvote(Integer downvote) { this.downvote = downvote; }

}
