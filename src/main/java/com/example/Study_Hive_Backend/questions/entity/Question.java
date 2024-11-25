package com.example.Study_Hive_Backend.questions.entity;
import com.example.Study_Hive_Backend.comments.entity.Comment;
import com.example.Study_Hive_Backend.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String category;
    private String authorEmail;// Store the author's email here

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

}