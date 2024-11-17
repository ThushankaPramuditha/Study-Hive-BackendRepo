// CommentRepository.java
package com.example.Study_Hive_Backend.comments.repository;

import com.example.Study_Hive_Backend.comments.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByQuestionId(Long questionId);
}
