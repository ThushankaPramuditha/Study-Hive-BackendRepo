package com.example.Study_Hive_Backend.profilesetup.dto;

import com.example.Study_Hive_Backend.profilesetup.entity.Profile;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileDTO {

    private String aboutMe;
    private String gender;
    private String adaptability;
    private String preferredLanguages;
    private String preferredStudyTime;
    private String profilePhotoUrl;
    private String studyGoal;
    private String studyingFor;
    private String university;
    private String username;
    private Integer userId;
    private String phoneNumber;
    private String emergencyContact;

    // No-argument constructor
    public ProfileDTO() {
    }

    // Constructor to initialize from Profile entity
    public ProfileDTO(Profile profile) {
        this.aboutMe = profile.getAboutMe();
        this.gender = profile.getGender();
        this.adaptability = profile.getAdaptability();
        this.preferredLanguages = profile.getPreferredLanguages();
        this.preferredStudyTime = profile.getPreferredStudyTime();
        this.profilePhotoUrl = profile.getProfilePhotoUrl();
        this.studyGoal = profile.getStudyGoal();
        this.studyingFor = profile.getStudyingFor();
        this.university = profile.getUniversity();
        this.username = profile.getUsername();
        this.userId = profile.getUser() != null ? profile.getUser().getId().intValue() : null; // Handle userId safely
        this.phoneNumber = profile.getPhoneNumber();
        this.emergencyContact = profile.getEmergencyContact();
    }
}
