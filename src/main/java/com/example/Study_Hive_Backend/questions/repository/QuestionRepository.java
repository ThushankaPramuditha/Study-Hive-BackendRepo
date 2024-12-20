package com.example.Study_Hive_Backend.questions.repository;

import com.example.Study_Hive_Backend.questions.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT COUNT(q) FROM Question q")
    Long countForumQuestions();
    long countByCreatedDateBetween(LocalDateTime start, LocalDateTime end);
    long countByCreatedDate(LocalDateTime createdDate);

    @Query("SELECT COUNT(q) FROM Question q WHERE q.createdDate BETWEEN :startDate AND :endDate")
    long getQuestionCountByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}

