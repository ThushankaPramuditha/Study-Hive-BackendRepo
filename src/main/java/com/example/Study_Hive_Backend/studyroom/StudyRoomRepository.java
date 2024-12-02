package com.example.Study_Hive_Backend.studyroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;


public interface StudyRoomRepository extends JpaRepository<StudyRoomEntity, Long> {
    @Query("SELECT COUNT(s) FROM StudyRoomEntity s")
    Long countStudyRooms();

    long countByCreatedDateBetween(LocalDateTime start, LocalDateTime end);

}