package com.example.Study_Hive_Backend.questions.dto;

import lombok.Data;

@Data
public class QuestionRequest {
    private String title;
    private String content;
    private String category;


}