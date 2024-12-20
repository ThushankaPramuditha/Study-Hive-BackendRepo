package com.example.Study_Hive_Backend.studyroom.repository;

import com.example.Study_Hive_Backend.studyroom.entity.StudyRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import java.util.List;

public interface StudyRoomRepository extends JpaRepository<StudyRoomEntity, Long> {

    List<StudyRoomEntity> findByOwnerId(Integer ownerId);

    @Query("SELECT COUNT(s) FROM StudyRoomEntity s")
    Long countStudyRooms();

    long countByCreatedDate(LocalDateTime createdDate);
    long countByCreatedDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT COUNT(s) FROM StudyRoomEntity s WHERE s.createdDate BETWEEN :startDate AND :endDate")
    long getStudyRoomCountByDateRange(LocalDateTime startDate, LocalDateTime endDate);

}

