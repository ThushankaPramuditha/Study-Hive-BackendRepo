//// CommentService.java
//package com.example.Study_Hive_Backend.comments.service;
//
//import com.example.Study_Hive_Backend.comments.dto.CommentRequest;
//import com.example.Study_Hive_Backend.comments.dto.CommentResponse;
//import com.example.Study_Hive_Backend.comments.entity.Comment;
//import com.example.Study_Hive_Backend.comments.repository.CommentRepository;
//import com.example.Study_Hive_Backend.questions.entity.Question;
//import com.example.Study_Hive_Backend.questions.repository.QuestionRepository;
//import com.example.Study_Hive_Backend.user.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class CommentService {
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Autowired
//    private QuestionRepository questionRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    // Method to add a comment to a specific question
//    public CommentResponse addCommentToQuestion(Long questionId, CommentRequest commentRequest) {
//
//
//        Question question = questionRepository.findById(questionId)
//                .orElseThrow(() -> new RuntimeException("Question not found"));
//
//
//        // Get user's full name using email
//        String fullName = getUserFullNameFromEmail(commentRequest.getAuthorEmail());
//
//        Comment comment = new Comment();
//        comment.setContent(commentRequest.getContent());
//        comment.setAuthorFullName(fullName);
//        comment.setQuestion(question);
//        comment.setUpvote(0);
//        comment.setDownvote(0);
//
//
//
//        Comment savedComment = commentRepository.save(comment);
//
//        CommentResponse commentResponse = new CommentResponse();
//        commentResponse.setId(savedComment.getId());
//        commentResponse.setContent(savedComment.getContent());
//        commentResponse.setAuthor(savedComment.getAuthorFullName());
//        commentResponse.setQuestionId(question.getId());
//        commentResponse.setUpvote(savedComment.getUpvote());
//        commentResponse.setDownvote(savedComment.getDownvote());
//
//
//
//        return commentResponse;
//    }
//    // Utility method to get user's full name from email
//    private String getUserFullNameFromEmail(String email) {
//        return userRepository.findByEmail(email)
//                .map(user -> user.getFirstname() + " " + user.getLastname())
//                .orElse("Unknown User");
//    }
//    // Method to get all comments for a specific question
//    public List<CommentResponse> getCommentsForQuestion(Long questionId) {
//        List<Comment> comments = commentRepository.findByQuestionId(questionId);
//
//        return comments.stream().map(comment -> {
//            CommentResponse response = new CommentResponse();
//            response.setId(comment.getId());
//            response.setContent(comment.getContent());
//            response.setAuthor(comment.getAuthorFullName());
//            response.setQuestionId(comment.getQuestion().getId());
//            response.setUpvote(comment.getUpvote());
//            response.setDownvote(comment.getDownvote());
//            return response;
//        }).collect(Collectors.toList());
//    }
//
//    // Upvote a comment
//    public void upvoteComment(Long commentId) {
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new RuntimeException("Comment not found"));
//        comment.setUpvote(comment.getUpvote() + 1);
//        commentRepository.save(comment);
//    }
//
//    // Downvote a comment
//    public void downvoteComment(Long commentId) {
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new RuntimeException("Comment not found"));
//        comment.setDownvote(comment.getDownvote() + 1);
//        commentRepository.save(comment);
//    }
//
//    // Reset votes for a comment
//    public void resetVotes(Long commentId) {
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new RuntimeException("Comment not found"));
//        comment.setUpvote(0);
//        comment.setDownvote(0);
//        commentRepository.save(comment);
//    }
//
//
//}


package com.example.Study_Hive_Backend.comments.service;

import com.example.Study_Hive_Backend.comments.dto.CommentRequest;
import com.example.Study_Hive_Backend.comments.dto.CommentResponse;
import com.example.Study_Hive_Backend.comments.entity.Comment;
import com.example.Study_Hive_Backend.comments.repository.CommentRepository;
import com.example.Study_Hive_Backend.questions.entity.Question;
import com.example.Study_Hive_Backend.questions.repository.QuestionRepository;
import com.example.Study_Hive_Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public CommentResponse addCommentToQuestion(Long questionId, CommentRequest commentRequest) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        String fullName = getUserFullNameFromEmail(commentRequest.getAuthorEmail());

        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setAuthorFullName(fullName);
        comment.setQuestion(question);
        comment.setUpvote(0);
        comment.setDownvote(0);

        Comment savedComment = commentRepository.save(comment);
        return mapToResponse(savedComment);
    }

    private String getUserFullNameFromEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> user.getFirstname() + " " + user.getLastname())
                .orElse("Unknown User");
    }



    public List<CommentResponse> getCommentsForQuestion(Long questionId) {
        List<Comment> comments = commentRepository.findByQuestionId(questionId);
        return comments.stream().map(this::mapToResponse).collect(Collectors.toList());
    }


    public Comment upvoteComment(Long commentId) {
        Comment comment = findCommentById(commentId);
        comment.setUpvote(comment.getUpvote() + 1);
        return commentRepository.save(comment); // Return the updated comment
    }

    public Comment downvoteComment(Long commentId) {
        Comment comment = findCommentById(commentId);
        comment.setDownvote(comment.getDownvote() + 1);
        return commentRepository.save(comment); // Return the updated comment
    }

    public void resetVotes(Long commentId) {
        Comment comment = findCommentById(commentId);
        comment.setUpvote(0);
        comment.setDownvote(0);
        commentRepository.save(comment);
    }


    private CommentResponse mapToResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getAuthorFullName(),
                comment.getQuestion().getId(),
                comment.getUpvote(),
                comment.getDownvote()
        );
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }
}
