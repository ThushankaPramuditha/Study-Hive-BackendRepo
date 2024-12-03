package com.example.Study_Hive_Backend.studyroom.dto;

import java.time.LocalDate;

public class BadgeDTO {
    private String name;
    private String description;
    private LocalDate earnedDate;

    public BadgeDTO(String name, String description, LocalDate earnedDate) {
        this.name = name;
        this.description = description;
        this.earnedDate = earnedDate;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEarnedDate() {
        return earnedDate;
    }

    public void setEarnedDate(LocalDate earnedDate) {
        this.earnedDate = earnedDate;
    }
}

