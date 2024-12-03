package com.example.Study_Hive_Backend.studyroom.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    private Integer ownerId;

    private String roomKey;  // Add this field to store the key for the room
    @OneToMany(mappedBy = "studyRoom", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
    private List<StudyRoomParticipantEntity> participants = new ArrayList<>();

    // Getters and Setters for roomKey
    public String getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
    }

}
