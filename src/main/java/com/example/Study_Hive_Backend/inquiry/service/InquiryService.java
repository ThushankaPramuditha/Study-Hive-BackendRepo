package com.example.Study_Hive_Backend.inquiry.service;

import com.example.Study_Hive_Backend.inquiry.entity.Inquiry;
import com.example.Study_Hive_Backend.inquiry.repository.InquiryRepository;
import com.example.Study_Hive_Backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    @Autowired
    // Constructor Injection
    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    public Inquiry createInquiry(Inquiry inquiry) {
        return inquiryRepository.save(inquiry);
    }

    public List<Inquiry> getUserInquiries() {
        // Logic to fetch user inquiries (for logged-in user, you can use the user ID passed from the controller)
        return inquiryRepository.findAll();
    }

    public List<Inquiry> getAllInquiries() {
        return inquiryRepository.findAll();
    }

    public List<Inquiry> searchInquiries(String query) {
        return inquiryRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query);
    }

    public Optional<Inquiry> getInquiryById(Long id) {
        return inquiryRepository.findById(id);
    }

    public void save(Inquiry inquiry) {
        inquiryRepository.save(inquiry);  // Save the inquiry, with user already set
    }

    // InquiryService method to fetch inquiries for a specific user
    public List<Inquiry> getUserInquiriesByUser(User user) {
        return inquiryRepository.findByUser(user); // Assuming you have this method in the repository
    }

    public boolean deleteReply(Long inquiryId) {
        Optional<Inquiry> inquiryOpt = inquiryRepository.findById(inquiryId);
        if (inquiryOpt.isPresent()) {
            Inquiry inquiry = inquiryOpt.get();
            inquiry.setAdminReply(null); // Set the reply to null (or delete it if it's a separate entity)
            inquiryRepository.save(inquiry); // Save the updated inquiry
            return true;
        }
        return false;
    }


}

