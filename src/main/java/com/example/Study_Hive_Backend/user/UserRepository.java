package com.example.Study_Hive_Backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer userId); // Corrected this method signature

    boolean existsByEmail(String email);


    @Query("SELECT COUNT(u) FROM User u")
    Long countUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.createdDate BETWEEN :start AND :end")
    long countByCreatedDateBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}

