package com.example.Study_Hive_Backend.admin.dto;

import java.util.List;


public class ReportData {

    private List<DailyReport> dailyReports;

    public ReportData(List<DailyReport> dailyReports) {
        this.dailyReports = dailyReports;
    }

    public List<DailyReport> getDailyReports() {
        return dailyReports;
    }

    public void setDailyReports(List<DailyReport> dailyReports) {
        this.dailyReports = dailyReports;
    }

    public static class DailyReport {

        private String dayName;
        private int users;
        private int studyRooms;
        private int questions;

        // Constructor to initialize the fields
        public DailyReport(String dayName, int users, int studyRooms, int questions) {
            this.dayName = dayName;
            this.users = users;
            this.studyRooms = studyRooms;
            this.questions = questions;
        }

        // Getters and setters
        public String getDayName() {
            return dayName;
        }

        public void setDayName(String dayName) {
            this.dayName = dayName;
        }

        public int getUsers() {
            return users;
        }

        public void setUsers(int users) {
            this.users = users;
        }

        public int getStudyRooms() {
            return studyRooms;
        }

        public void setStudyRooms(int studyRooms) {
            this.studyRooms = studyRooms;
        }

        public int getQuestions() {
            return questions;
        }

        public void setQuestions(int questions) {
            this.questions = questions;
        }
    }
}
