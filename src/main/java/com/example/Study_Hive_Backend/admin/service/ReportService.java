package com.example.Study_Hive_Backend.admin.service;



import com.example.Study_Hive_Backend.admin.dto.DaySummaryDTO;
import com.example.Study_Hive_Backend.questions.repository.QuestionRepository;
import com.example.Study_Hive_Backend.studyroom.StudyRoomRepository;
import com.example.Study_Hive_Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public List<DaySummaryDTO> getPast7DaysSummary() {
        LocalDate today = LocalDate.now();
        List<DaySummaryDTO> summaryList = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.minusDays(i);
            String dayOfWeek = date.getDayOfWeek().toString().substring(0, 3).toUpperCase();

            // Fetch counts for each entity (users, study rooms, questions)
            long userCount = userRepository.getUserCountByDateRange(date.atStartOfDay(), date.atTime(23, 59, 59));
            long studyRoomCount = studyRoomRepository.getStudyRoomCountByDateRange(date.atStartOfDay(), date.atTime(23, 59, 59));
            long questionCount = questionRepository.getQuestionCountByDateRange(date.atStartOfDay(), date.atTime(23, 59, 59));

            DaySummaryDTO dailySummary = new DaySummaryDTO(dayOfWeek, userCount, studyRoomCount, questionCount);
            summaryList.add(dailySummary);
        }

        return summaryList;
    }
}