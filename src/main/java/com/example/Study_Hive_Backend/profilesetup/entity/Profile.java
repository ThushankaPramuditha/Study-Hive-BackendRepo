package com.example.Study_Hive_Backend.profilesetup.entity;


import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "profiles")
@Data
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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