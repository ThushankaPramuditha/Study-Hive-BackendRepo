package com.example.Study_Hive_Backend.inquiry.controller;

import com.example.Study_Hive_Backend.auth.AuthenticationService;
import com.example.Study_Hive_Backend.inquiry.dto.InquiryDTO;
import com.example.Study_Hive_Backend.inquiry.entity.Inquiry;
import com.example.Study_Hive_Backend.inquiry.repository.InquiryRepository;
import com.example.Study_Hive_Backend.inquiry.service.InquiryService;
import com.example.Study_Hive_Backend.user.User;
import com.example.Study_Hive_Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private AuthenticationService authenticationService;


    @Autowired
    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }


//    // Endpoint to create a new inquiry
//    @PostMapping
//    public ResponseEntity<Inquiry> createInquiry(@RequestBody Inquiry inquiry) {
//        Inquiry createdInquiry = inquiryService.createInquiry(inquiry);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdInquiry);
//    }

//    @PostMapping("/create")
//    public ResponseEntity<?> createInquiry(@RequestBody Inquiry inquiry) {
//        try {
//            // Get the current logged-in user by their email
//            User user = authenticationService.getCurrentUser();
//
//            // Set the User object in the Inquiry
//            inquiry.setUser(user);  // Associate the logged-in user with the inquiry
//
//            // Save the inquiry
//            inquiryService.save(inquiry);
//
//            return ResponseEntity.ok(inquiry);
//        } catch (RuntimeException ex) {
//            // Handle the case when user is not found or authentication failed
//            return ResponseEntity.status(403).body(ex.getMessage());
//        }
//    }

    @PostMapping("/create")
    public ResponseEntity<?> createInquiry(@RequestBody Inquiry inquiry) {
        try {
            // Get the current logged-in user by their email
            User user = authenticationService.getCurrentUser();

            // Set the User object in the Inquiry
            inquiry.setUser(user);  // Associate the logged-in user with the inquiry

            // Set first name and last name from the logged-in user
            inquiry.setFirstName(user.getFirstname());
            inquiry.setLastName(user.getLastname());

            // Save the inquiry
            inquiryService.save(inquiry);

            return ResponseEntity.ok(inquiry);
        } catch (RuntimeException ex) {
            // Handle the case when user is not found or authentication failed
            return ResponseEntity.status(403).body(ex.getMessage());
        }
    }


    // Endpoint to get all inquiries for the logged-in user
    @GetMapping("/user")
    public ResponseEntity<List<Inquiry>> getUserInquiries() {
        // Get the currently authenticated user
        User user = authenticationService.getCurrentUser();

        // Fetch the inquiries by the user
        List<Inquiry> inquiries = inquiryService.getUserInquiriesByUser(user);

        if (inquiries.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(inquiries);
    }

    // Endpoint to get all inquiries (admin access)
    @GetMapping("/all")
    public ResponseEntity<List<Inquiry>> getAllInquiries() {
        List<Inquiry> inquiries = inquiryService.getAllInquiries();
        return ResponseEntity.ok(inquiries);
    }

    // Endpoint to search inquiries based on title or content
    @GetMapping("/search")
    public ResponseEntity<List<Inquiry>> searchInquiries(@RequestParam String query) {
        List<Inquiry> inquiries = inquiryService.searchInquiries(query);
        return ResponseEntity.ok(inquiries);
    }

    // Endpoint to reply to an inquiry
    @PostMapping("/{id}/reply")
    public ResponseEntity<?> replyToInquiry(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String adminReply = requestBody.get("adminReply");

        // Get the inquiry and set the adminReply as a plain string
        Optional<Inquiry> inquiryOptional = inquiryService.getInquiryById(id);  // Use service method to get the inquiry
        if (inquiryOptional.isPresent()) {
            Inquiry inquiry = inquiryOptional.get();
            inquiry.setAdminReply(adminReply); // Set the reply as a plain string
            inquiry.setRepliedAt(LocalDateTime.now());
            inquiryService.save(inquiry);  // Save updated inquiry
            return ResponseEntity.ok(inquiry);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inquiry not found");
    }


    @DeleteMapping("/{id}/reply")
    public ResponseEntity<?> deleteReply(@PathVariable Long id) {
        Optional<Inquiry> inquiryOptional = inquiryRepository.findById(id);

        if (!inquiryOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inquiry not found");
        }

        Inquiry inquiry = inquiryOptional.get();

        // Check if the adminReply is present
        if (inquiry.getAdminReply() != null) {
            inquiry.setAdminReply(null); // Remove the reply
            inquiryRepository.save(inquiry); // Save the updated inquiry object
            return ResponseEntity.ok("Reply deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reply to delete");
    }



}
