package com.example.Study_Hive_Backend.questions.dto;

import lombok.Data;

@Data
public class QuestionResponse {
    private Long id;
    private String content;
    private String category;
    private String authorEmail;
    private String authorFullName;// Add the email field

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }


}