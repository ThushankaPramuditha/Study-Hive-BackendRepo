package com.example.Study_Hive_Backend.profilesetup.dto;


import lombok.Data;

@Data
public class ProfileDTO {
    private String firstName;
    private String lastName;
    private String gender;
    private String university;
    private String studyingFor;
    private String levelOfKnowledge;
    private String preferredLanguage;
    private String preferredStudyTime;
    private String studyGoal;
    private String aboutMe;
    private String profilePhotoUrl;
}