package com.example.Study_Hive_Backend.studyroom.service;

import com.example.Study_Hive_Backend.studyroom.dto.BadgeDTO;
import com.example.Study_Hive_Backend.studyroom.dto.LeaderboardEntryDTO;
import com.example.Study_Hive_Backend.studyroom.dto.StudyStatisticsDTO;
import com.example.Study_Hive_Backend.studyroom.repository.UserRoomTimeRepository;
import com.example.Study_Hive_Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class StudyStatisticsService {
    @Autowired
    private UserRoomTimeRepository userRoomTimeRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public StudyStatisticsDTO getUserStatistics(Integer userId) {
        StudyStatisticsDTO stats = new StudyStatisticsDTO();

        CompletableFuture<Long> totalHoursFuture = CompletableFuture.supplyAsync(() -> userRoomTimeRepository.sumTimeSpentByUserId(userId) / 3600);
        CompletableFuture<Double> avgHoursFuture = CompletableFuture.supplyAsync(() -> userRoomTimeRepository.averageTimeSpentByUserId(userId) / 3600);
        CompletableFuture<Integer> rankFuture = CompletableFuture.supplyAsync(() -> calculateUserRank(userId));
        CompletableFuture<Map<String, Long>> dailyHoursMapFuture = CompletableFuture.supplyAsync(() -> getDailyStudyHours(userId));
        CompletableFuture<List<BadgeDTO>> badgesFuture = CompletableFuture.supplyAsync(() -> calculateBadges(userId));

        CompletableFuture.allOf(totalHoursFuture, avgHoursFuture, rankFuture, dailyHoursMapFuture, badgesFuture).join();

        stats.setTotalStudyHours(totalHoursFuture.join());
        stats.setAverageStudyHours(avgHoursFuture.join());
        stats.setRank(rankFuture.join());
        stats.setDailyStudyHours(dailyHoursMapFuture.join());
        stats.setBadges(badgesFuture.join());

        return stats;
    }

    private Map<String, Long> getDailyStudyHours(Integer userId) {
        LocalDate startDate = LocalDate.now().minusDays(6);
        List<Object[]> dailyHours = userRoomTimeRepository.getDailyStudyHours(userId, startDate);
        Map<String, Long> dailyHoursMap = new LinkedHashMap<>(); // Use LinkedHashMap to maintain order

        // Initialize all days with 0 hours
        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);
            dailyHoursMap.put(date.toString(), 0L);
        }

        // Update with actual study hours
        for (Object[] day : dailyHours) {
            LocalDate date = (LocalDate) day[0];
            Long hours = (Long) day[1] / 3600; // Convert seconds to hours
            dailyHoursMap.put(date.toString(), hours);
        }

        return dailyHoursMap;
    }

    private List<BadgeDTO> calculateBadges(Integer userId) {
        List<BadgeDTO> badges = new ArrayList<>();
        LocalDate startOfWeek = LocalDate.now().minusDays(6);
        long weeklyHours = userRoomTimeRepository.getWeeklyStudyHours(userId, startOfWeek) / 3600;

        if (weeklyHours < 2) {
            badges.add(new BadgeDTO("Newbie", "Studied for less than 2 hours this week", LocalDate.now()));
        } else if (weeklyHours < 5) {
            badges.add(new BadgeDTO("Acrobat", "Studied for 2-5 hours this week", LocalDate.now()));
        } else if (weeklyHours < 10) {
            badges.add(new BadgeDTO("Performer", "Studied for 5-10 hours this week", LocalDate.now()));
        } else {
            badges.add(new BadgeDTO("Nerd", "Studied for 10+ hours this week", LocalDate.now()));
        }

        return badges;
    }

    private Integer calculateUserRank(Integer userId) {
        List<Object[]> topUsers = userRoomTimeRepository.getTopUsersByStudyHours();
        for (int i = 0; i < topUsers.size(); i++) {
            if (userId.equals(topUsers.get(i)[0])) {
                return i + 1;
            }
        }
        return topUsers.size() + 1; // If user not found in the list, they are last
    }

    @Transactional(readOnly = true)
    public List<LeaderboardEntryDTO> getLeaderboard() {
        List<Object[]> topUsers = userRoomTimeRepository.getTopUsersByStudyHours();
        List<LeaderboardEntryDTO> leaderboard = new ArrayList<>();

        for (int i = 0; i < topUsers.size(); i++) {
            Object[] user = topUsers.get(i);
            Integer userId = (Integer) user[0];
            Long totalHours = (Long) user[1] / 3600; // Convert seconds to hours
            String username = userRepository.findById(userId).map(u -> u.getUsername()).orElse("Unknown");
            leaderboard.add(new LeaderboardEntryDTO(userId, username, totalHours, i + 1));
        }

        return leaderboard;
    }
}

