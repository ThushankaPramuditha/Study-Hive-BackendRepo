package com.example.Study_Hive_Backend.studyroom.repository;

import com.example.Study_Hive_Backend.studyroom.entity.StudyRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRoomRepository extends JpaRepository<StudyRoomEntity, Long> {
}
