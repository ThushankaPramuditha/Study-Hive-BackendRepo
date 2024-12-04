package com.example.Study_Hive_Backend.newcommunity.subgroup;

import com.example.Study_Hive_Backend.newcommunity.subgroup.Subgroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubgroupRepository extends JpaRepository<Subgroup, Long> {
    List<Subgroup> findByCommunityId(Long communityId);
}

