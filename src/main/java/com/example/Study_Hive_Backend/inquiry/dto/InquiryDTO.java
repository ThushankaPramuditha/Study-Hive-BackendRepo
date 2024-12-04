package com.example.Study_Hive_Backend.inquiry.dto;

import com.example.Study_Hive_Backend.inquiry.entity.Inquiry;
import com.example.Study_Hive_Backend.user.User;
import lombok.Data;


public class InquiryDTO {
    private String title;
    private String content;
    private Integer userId; // To map logged-in user ID
    private String firstName;  // User's first name
    private String lastName;
    private Long id;
    private String name;


    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    // Constructor to convert from Inquiry entity to DTO
    public InquiryDTO(Inquiry inquiry) {
        this.id = inquiry.getId();
        this.title = inquiry.getTitle();
        this.content = inquiry.getContent();
        if (inquiry.getUser() != null) {
            User user = inquiry.getUser();
            this.name = user.getFirstname() + " " + user.getLastname();
            this.userId = user.getId();
            this.firstName = user.getFirstname(); // Add first name
            this.lastName = user.getLastname();   // Add last name
        }

    }
}

