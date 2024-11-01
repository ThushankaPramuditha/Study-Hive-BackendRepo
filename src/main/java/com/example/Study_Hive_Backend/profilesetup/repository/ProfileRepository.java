package com.example.Study_Hive_Backend.profilesetup.repository;

import com.example.Study_Hive_Backend.profilesetup.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // Custom query methods if needed
}
