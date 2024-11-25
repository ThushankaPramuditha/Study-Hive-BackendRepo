package com.example.Study_Hive_Backend.admin.service;

import com.example.Study_Hive_Backend.questions.repository.QuestionRepository;
import com.example.Study_Hive_Backend.studyroom.StudyRoomRepository;
import com.example.Study_Hive_Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private QuestionRepository forumQuestionRepository;

    // Method to get the dashboard statistics
    public Map<String, Long> getDashboardStats() {
        Map<String, Long> stats = new HashMap<>();

        // Counting users, study rooms, and forum questions
        stats.put("users", userRepository.countUsers());
        stats.put("studyRooms", studyRoomRepository.countStudyRooms());
        stats.put("forumQuestions", forumQuestionRepository.countForumQuestions());

        return stats;
    }
}
