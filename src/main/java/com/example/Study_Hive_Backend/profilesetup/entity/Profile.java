package com.example.Study_Hive_Backend.profilesetup.entity;


//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Setter
//@Getter
//@Entity
//@Table(name = "profiles")
//public class Profile {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String username;
//    private String gender;
//    private String adaptability;
//    private String preferredLanguages;
//    private String preferredStudyTime;
//    private String studyGoal;
//    private String aboutMe;
//    private String studyingFor;
//    private String university;
//    private String profilePhotoUrl;
//
//}

import com.example.Study_Hive_Backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String gender;
    private String adaptability;
    private String preferredLanguages;
    private String preferredStudyTime;
    private String studyGoal;
    private String aboutMe;
    private String studyingFor;
    private String university;
    private String profilePhotoUrl;

    // Add the relationship with User
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
