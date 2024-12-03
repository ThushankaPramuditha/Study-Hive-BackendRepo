package com.example.Study_Hive_Backend.community.repository;



import com.example.Study_Hive_Backend.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    List<Community> findByNameContainingIgnoreCase(String name);
}
