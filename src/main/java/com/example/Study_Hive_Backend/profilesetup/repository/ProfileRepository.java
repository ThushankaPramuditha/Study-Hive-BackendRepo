package com.example.Study_Hive_Backend.profilesetup.repository;

import com.example.Study_Hive_Backend.profilesetup.entity.Profile;
import com.example.Study_Hive_Backend.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserId(Integer userId);
    Optional<User> findByEmail(String email);
    boolean existsByUserId(Integer userId);


    @Modifying
    @Transactional
    @Query("DELETE FROM Profile p WHERE p.user.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);


}