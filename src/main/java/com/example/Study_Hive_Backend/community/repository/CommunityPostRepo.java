package com.example.Study_Hive_Backend.community.repository;


import com.example.Study_Hive_Backend.community.entity.CommunityPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPostRepo extends JpaRepository<CommunityPostEntity, Integer> {
}
