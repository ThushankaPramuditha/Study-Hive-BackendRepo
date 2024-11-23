// Comment.java
package com.example.Study_Hive_Backend.comments.entity;

import com.example.Study_Hive_Backend.questions.entity.Question;
import jakarta.persistence.*;

import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String authorFullName;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int upvote;
    // Default value is 0
    @Column(nullable = false, columnDefinition = "integer default 0")
    private int downvote;
    // Default value is 0

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    // Getter and Setter for authorFullName if not using Lombok
    public String getAuthorFullName() {
        return authorFullName;
    }

    public void setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
    }
    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    // Getter and Setter for upvote
    public int getUpvote() {
        return upvote;
    }

    public void setUpvote(int upvote) {
        this.upvote = upvote;
    }

    // Getter and Setter for downvote
    public int getDownvote() {
        return downvote;
    }

    public void setDownvote(int downvote) {
        this.downvote = downvote;
    }

}
