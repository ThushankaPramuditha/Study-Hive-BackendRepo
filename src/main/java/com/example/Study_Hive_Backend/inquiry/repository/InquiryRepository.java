package com.example.Study_Hive_Backend.inquiry.repository;

import com.example.Study_Hive_Backend.inquiry.entity.Inquiry;
import com.example.Study_Hive_Backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    // Custom query to find inquiries by userId (user_id is Integer)
    List<Inquiry> findByUserId(Integer userId);
    // InquiryRepository method to find inquiries by user
    List<Inquiry> findByUser(User user);



    // Additional query method to search by title or content
    List<Inquiry> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
}
