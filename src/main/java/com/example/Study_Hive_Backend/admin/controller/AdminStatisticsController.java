package com.example.Study_Hive_Backend.admin.controller;

//import com.example.Study_Hive_Backend.admin.dto.ReportData;
import com.example.Study_Hive_Backend.admin.dto.StatisticsResponse;
import com.example.Study_Hive_Backend.admin.service.AdminService;
import com.example.Study_Hive_Backend.questions.repository.QuestionRepository;
import com.example.Study_Hive_Backend.studyroom.StudyRoomRepository;
import com.example.Study_Hive_Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminStatisticsController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository; // Replace with your user repository
    @Autowired
    private StudyRoomRepository studyRoomRepository; // Replace with your study room repository
    @Autowired
    private QuestionRepository forumQuestionRepository;

//    @GetMapping("/dashboard-stats")
//    public ResponseEntity<Map<String, Long>> getDashboardStats() {
//        Map<String, Long> stats = adminService.getDashboardStats();
//        return ResponseEntity.ok(stats);
//    }

    @GetMapping("/dashboard-stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        // Today's statistics
        long newUsersToday = userRepository.countByCreatedDateBetween(startOfDay, endOfDay);
        long newStudyRoomsToday = studyRoomRepository.countByCreatedDateBetween(startOfDay, endOfDay);
        long newForumQuestionsToday = forumQuestionRepository.countByCreatedDateBetween(startOfDay, endOfDay);

        // Total statistics
        long totalUsers = userRepository.count();
        long totalStudyRooms = studyRoomRepository.count();
        long totalForumQuestions = forumQuestionRepository.count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("newUsersToday", newUsersToday);
        stats.put("newStudyRoomsToday", newStudyRoomsToday);
        stats.put("newForumQuestionsToday", newForumQuestionsToday);
        stats.put("totalUsers", totalUsers);
        stats.put("totalStudyRooms", totalStudyRooms);
        stats.put("totalForumQuestions", totalForumQuestions);

        return ResponseEntity.ok(stats);
    }
}