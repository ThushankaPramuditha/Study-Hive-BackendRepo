package com.example.Study_Hive_Backend.studyroom.controller;

import com.example.Study_Hive_Backend.studyroom.dto.LeaderboardEntryDTO;
import com.example.Study_Hive_Backend.studyroom.dto.StudyStatisticsDTO;
import com.example.Study_Hive_Backend.studyroom.service.StudyStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StudyStatisticsController {
    @Autowired
    private StudyStatisticsService studyStatisticsService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<StudyStatisticsDTO> getUserStatistics(@PathVariable Integer userId) {
        StudyStatisticsDTO stats = studyStatisticsService.getUserStatistics(userId);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardEntryDTO>> getLeaderboard() {
        List<LeaderboardEntryDTO> leaderboard = studyStatisticsService.getLeaderboard();
        return ResponseEntity.ok(leaderboard);
    }
}

