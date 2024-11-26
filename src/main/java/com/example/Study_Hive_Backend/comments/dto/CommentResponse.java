//// CommentResponse.java
//package com.example.Study_Hive_Backend.comments.dto;
//
//public class CommentResponse {
//    private Long id;
//    private String content;
//    private String author;
//    private Long questionId;
//
//    // Getters and Setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getContent() { return content; }
//    public void setContent(String content) { this.content = content; }
//
//    public String getAuthor() { return author; }
//    public void setAuthor(String author) { this.author = author; }
//
//    public Long getQuestionId() { return questionId; }
//    public void setQuestionId(Long questionId) { this.questionId = questionId; }
//}

package com.example.Study_Hive_Backend.comments.dto;

public class CommentResponse {
    private Long id;
    private String content;
    private String author;  // This is now the full name of the author
    private Long questionId;
    private int upvote = 0;
    private int downvote = 0;



    // Default constructor
    public CommentResponse() {}

    // Constructor for easy initialization
    public CommentResponse(Long id, String content, String author, Long questionId ,int upvote, int downvote) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.questionId = questionId;
        this.upvote = upvote;
        this.downvote = downvote;

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public int getUpvote() {
        return upvote;
    }

    public void setUpvote(int upvote) {
        this.upvote = upvote;
    }

    public int getDownvote() {
        return downvote;
    }

    public void setDownvote(int downvote) {
        this.downvote = downvote;
    }


    // Override toString() for better logging
    @Override
    public String toString() {
        return "CommentResponse{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", questionId=" + questionId +
                ", upvote=" + upvote +
                ", downvote=" + downvote +
                '}';
    }

}

