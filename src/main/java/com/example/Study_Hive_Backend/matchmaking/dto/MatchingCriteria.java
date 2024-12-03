package com.example.Study_Hive_Backend.matchmaking.dto;

import lombok.Data;

@Data
public class MatchingCriteria {
    private String studyingFor;
    private String preferredStudyTime;
    private String adaptability;
    private String studyGoal;
    private String additionalInfo;
}

