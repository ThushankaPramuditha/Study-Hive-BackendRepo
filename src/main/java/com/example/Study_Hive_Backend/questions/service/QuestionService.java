package com.example.Study_Hive_Backend.questions.service;
//
//import com.example.Study_Hive_Backend.questions.dto.QuestionDTO;
//import com.example.Study_Hive_Backend.questions.entity.Question;
//import com.example.Study_Hive_Backend.questions.repository.QuestionRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class QuestionService {
//
//    @Autowired
//    private QuestionRepository questionRepository;
//
//    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
//        Question question = new Question();
//        question.setTitle(questionDTO.getTitle());
//        question.setContent(questionDTO.getContent());
//        question.setCategory(questionDTO.getCategory());
//        question = questionRepository.save(question);
//        questionDTO.setId(question.getId());
//        return questionDTO;
//    }
//
//    public List<QuestionDTO> getAllQuestions() {
//        return questionRepository.findAll().stream()
//                .map(question -> {
//                    QuestionDTO dto = new QuestionDTO();
//                    dto.setId(question.getId());
//                    dto.setTitle(question.getTitle());
//                    dto.setContent(question.getContent());
//                    dto.setCategory(question.getCategory());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//}


import com.example.Study_Hive_Backend.questions.dto.QuestionRequest;
import com.example.Study_Hive_Backend.questions.dto.QuestionResponse;
import com.example.Study_Hive_Backend.questions.entity.Question;
import com.example.Study_Hive_Backend.questions.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public QuestionResponse createQuestion(QuestionRequest questionRequest) {
        Question question = new Question();
        question.setContent(questionRequest.getContent());
        question.setCategory(questionRequest.getCategory());
        questionRepository.save(question);
        return mapToResponse(question);
    }

    public List<QuestionResponse> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private QuestionResponse mapToResponse(Question question) {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setId(question.getId());
        questionResponse.setContent(question.getContent());
        questionResponse.setCategory(question.getCategory());
        return questionResponse;
    }
}
