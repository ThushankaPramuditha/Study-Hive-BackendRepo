package com.example.Study_Hive_Backend.newcommunity.announcements;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByCommunityId(Long communityId);
}

