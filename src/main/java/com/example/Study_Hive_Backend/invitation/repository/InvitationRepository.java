package com.example.Study_Hive_Backend.invitation.repository;

import com.example.Study_Hive_Backend.invitation.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByReceiverId(Long receiverId);
    List<Invitation> findBySenderId(Long senderId);
}