package com.example.Study_Hive_Backend.studyroom;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "studyroom")
public class StudyRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;
    private String description;
    private int participantCount;
    private boolean isPublic;
    private boolean acceptTerms;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;
}
