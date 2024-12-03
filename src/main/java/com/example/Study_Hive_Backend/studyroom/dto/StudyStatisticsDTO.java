package com.example.Study_Hive_Backend.studyroom.dto;

import java.util.List;
import java.util.Map;

public class StudyStatisticsDTO {
    private Double totalStudyHours;
    private Double averageStudyHours;
    private Integer rank;
    private Map<String, Long> dailyStudyHours;
    private List<BadgeDTO> badges;

    // Getters and setters
    public Double getTotalStudyHours() {
        return totalStudyHours;
    }

    public void setTotalStudyHours(Double totalStudyHours) {
        this.totalStudyHours = totalStudyHours;
    }

    public Double getAverageStudyHours() {
        return averageStudyHours;
    }

    public void setAverageStudyHours(Double averageStudyHours) {
        this.averageStudyHours = averageStudyHours;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Map<String, Long> getDailyStudyHours() {
        return dailyStudyHours;
    }

    public void setDailyStudyHours(Map<String, Long> dailyStudyHours) {
        this.dailyStudyHours = dailyStudyHours;
    }

    public List<BadgeDTO> getBadges() {
        return badges;
    }

    public void setBadges(List<BadgeDTO> badges) {
        this.badges = badges;
    }
}

