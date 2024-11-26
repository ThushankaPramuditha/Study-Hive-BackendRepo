package com.example.Study_Hive_Backend.studyroom.repository;

import com.example.Study_Hive_Backend.studyroom.entity.StudyRoomParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRoomParticipantRepository extends JpaRepository<StudyRoomParticipantEntity, Long> {
    List<StudyRoomParticipantEntity> findByUserIdAndStatus(Integer userId, StudyRoomParticipantEntity.ParticipantStatus status);
}