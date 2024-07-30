package com.example.Study_Hive_Backend.questions.controller;


import com.example.Study_Hive_Backend.questions.dto.QuestionRequest;
import com.example.Study_Hive_Backend.questions.dto.QuestionResponse;
import com.example.Study_Hive_Backend.questions.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public QuestionResponse createQuestion(@RequestBody QuestionRequest questionRequest) {
        return questionService.createQuestion(questionRequest);
    }

    @GetMapping
    public List<QuestionResponse> getAllQuestions() {
        return questionService.getAllQuestions();
    }
}
