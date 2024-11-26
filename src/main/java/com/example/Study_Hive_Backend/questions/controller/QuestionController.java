package com.example.Study_Hive_Backend.questions.controller;


import com.example.Study_Hive_Backend.comments.entity.Comment;
import com.example.Study_Hive_Backend.questions.dto.QuestionRequest;
import com.example.Study_Hive_Backend.questions.dto.QuestionResponse;
import com.example.Study_Hive_Backend.questions.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Study_Hive_Backend.comments.dto.CommentRequest;
import com.example.Study_Hive_Backend.comments.dto.CommentResponse;
import com.example.Study_Hive_Backend.comments.service.CommentService;



import java.util.List;

import static org.springframework.security.config.Elements.JWT;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

//    @PostMapping
//    public QuestionResponse createQuestion(@RequestBody QuestionRequest questionRequest) {
//        return questionService.createQuestion(questionRequest);
//    }
@PostMapping
public QuestionResponse createQuestion(@RequestBody QuestionRequest questionRequest, @RequestParam String userEmail) {
    return questionService.createQuestion(questionRequest, userEmail);
}


    @GetMapping
    public List<QuestionResponse> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    // Endpoint to add a comment to a specific question
    @PostMapping("/{questionId}/comments")
    public CommentResponse addCommentToQuestion(
            @PathVariable Long questionId,
            @RequestBody CommentRequest commentRequest) {
        return commentService.addCommentToQuestion(questionId, commentRequest);
    }

    // Endpoint to retrieve all comments for a specific question
    @GetMapping("/{questionId}/comments")
    public List<CommentResponse> getCommentsForQuestion(@PathVariable Long questionId) {
        return commentService.getCommentsForQuestion(questionId);
    }

    @PostMapping("/{questionId}/comments/{commentId}/upvote")
    public ResponseEntity<Comment> upvoteComment(@PathVariable Long questionId, @PathVariable Long commentId) {
        Comment updatedComment = commentService.upvoteComment(commentId);
        return ResponseEntity.ok(updatedComment);
    }

    @PostMapping("/{questionId}/comments/{commentId}/downvote")
    public ResponseEntity<Comment> downvoteComment(@PathVariable Long questionId, @PathVariable Long commentId) {
        Comment updatedComment = commentService.downvoteComment(commentId);
        return ResponseEntity.ok(updatedComment);
    }


}
