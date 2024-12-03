package com.example.Study_Hive_Backend.admin.dto;



public class StatisticsResponse {

    private int newUsers;
    private int newStudyRooms;
    private int newCommunities;
    private int newQuestions;

    // Constructor to initialize the fields
    public StatisticsResponse(int newUsers, int newStudyRooms, int newCommunities, int newQuestions) {
        this.newUsers = newUsers;
        this.newStudyRooms = newStudyRooms;
        this.newCommunities = newCommunities;
        this.newQuestions = newQuestions;
    }

    // Getters and setters
    public int getNewUsers() {
        return newUsers;
    }

    public void setNewUsers(int newUsers) {
        this.newUsers = newUsers;
    }

    public int getNewStudyRooms() {
        return newStudyRooms;
    }

    public void setNewStudyRooms(int newStudyRooms) {
        this.newStudyRooms = newStudyRooms;
    }

    public int getNewCommunities() {
        return newCommunities;
    }

    public void setNewCommunities(int newCommunities) {
        this.newCommunities = newCommunities;
    }

    public int getNewQuestions() {
        return newQuestions;
    }

    public void setNewQuestions(int newQuestions) {
        this.newQuestions = newQuestions;
    }
}

