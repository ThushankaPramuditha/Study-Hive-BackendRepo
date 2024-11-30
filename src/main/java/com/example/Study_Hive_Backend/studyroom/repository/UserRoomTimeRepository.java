package com.example.Study_Hive_Backend.studyroom.repository;

import com.example.Study_Hive_Backend.studyroom.entity.UserRoomTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRoomTimeRepository extends JpaRepository<UserRoomTime, Long> {

    @Query("SELECT SUM(u.timeSpent) FROM UserRoomTime u WHERE u.userId = :userId")
    Long sumTimeSpentByUserId(@Param("userId") Integer userId);

    @Query("SELECT AVG(u.timeSpent) FROM UserRoomTime u WHERE u.userId = :userId")
    Double averageTimeSpentByUserId(@Param("userId") Integer userId);

    @Query("SELECT u.studyDate as date, SUM(u.timeSpent) as hours FROM UserRoomTime u WHERE u.userId = :userId AND u.studyDate >= :startDate GROUP BY u.studyDate")
    List<Object[]> getDailyStudyHours(@Param("userId") Integer userId, @Param("startDate") LocalDate startDate);

    @Query("SELECT u.userId, SUM(u.timeSpent) as totalTime FROM UserRoomTime u GROUP BY u.userId ORDER BY totalTime DESC")
    List<Object[]> getTopUsersByStudyHours();

    @Query("SELECT SUM(u.timeSpent) FROM UserRoomTime u WHERE u.userId = :userId AND u.studyDate >= :startDate")
    Long getWeeklyStudyHours(@Param("userId") Integer userId, @Param("startDate") LocalDate startDate);

    @Query("SELECT DISTINCT u.userId FROM UserRoomTime u")
    List<Integer> findAllDistinctUserIds();
}

