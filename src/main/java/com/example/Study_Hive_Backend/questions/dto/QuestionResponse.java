package com.example.Study_Hive_Backend.questions.dto;

import lombok.Data;

@Data
public class QuestionResponse {
    private Long id;
    private String content;
    private String category;
}