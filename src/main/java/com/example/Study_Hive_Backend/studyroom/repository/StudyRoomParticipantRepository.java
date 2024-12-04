package com.example.Study_Hive_Backend.studyroom.repository;

import com.example.Study_Hive_Backend.studyroom.entity.StudyRoomParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyRoomParticipantRepository extends JpaRepository<StudyRoomParticipantEntity, Long> {
    List<StudyRoomParticipantEntity> findByUserIdAndStatus(Integer userId, StudyRoomParticipantEntity.ParticipantStatus status);

    List<StudyRoomParticipantEntity> findByStudyRoomIdAndStatus(Long roomId, StudyRoomParticipantEntity.ParticipantStatus status);

    Optional<StudyRoomParticipantEntity> findByStudyRoomIdAndUserId(Long roomId, Integer userId);

    List<StudyRoomParticipantEntity> findAllByStudyRoomIdAndUserId(Long roomId, Integer userId);


}
