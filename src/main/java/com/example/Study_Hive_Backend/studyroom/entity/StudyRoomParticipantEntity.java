package com.example.Study_Hive_Backend.studyroom.entity;

import com.example.Study_Hive_Backend.studyroom.entity.StudyRoomEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "studyroom_participants")
public class StudyRoomParticipantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studyroom_id", nullable = false)
    @JsonIgnore
    private StudyRoomEntity studyRoom;

    private Integer userId;

    @Enumerated(EnumType.STRING)
    private ParticipantStatus status;

    public enum ParticipantStatus {
        PENDING,
        ACCEPTED,
        DECLINED
    }
}
