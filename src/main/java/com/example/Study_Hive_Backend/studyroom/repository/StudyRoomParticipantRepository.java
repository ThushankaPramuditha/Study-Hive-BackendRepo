package com.example.Study_Hive_Backend.studyroom.repository;

import com.example.Study_Hive_Backend.studyroom.entity.StudyRoomParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRoomParticipantRepository extends JpaRepository<StudyRoomParticipantEntity, Long> {
}