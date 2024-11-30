package com.example.Study_Hive_Backend.studyroom.dto;

public class LeaderboardEntryDTO {
    private Integer userId;
    private String username;
    private Long totalHours;
    private Integer rank;

    public LeaderboardEntryDTO(Integer userId, String username, Long totalHours, Integer rank) {
        this.userId = userId;
        this.username = username;
        this.totalHours = totalHours;
        this.rank = rank;
    }

    // Getters and setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Long totalHours) {
        this.totalHours = totalHours;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}

