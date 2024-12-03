package com.example.Study_Hive_Backend.studyroom.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "user_room_time")
@Data
public class UserRoomTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "time_spent")
    private Long timeSpent;

    @Column(name = "study_date")
    private LocalDate studyDate;

    @PrePersist
    protected void onCreate() {
        studyDate = LocalDate.now();
    }
}