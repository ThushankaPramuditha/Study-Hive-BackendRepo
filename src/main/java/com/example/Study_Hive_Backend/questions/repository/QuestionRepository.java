package com.example.Study_Hive_Backend.questions.repository;

import com.example.Study_Hive_Backend.questions.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}