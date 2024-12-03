package com.example.Study_Hive_Backend.matchmaking.controller;

import com.example.Study_Hive_Backend.matchmaking.dto.MatchingCriteria;
import com.example.Study_Hive_Backend.matchmaking.service.MatchingService;
import com.example.Study_Hive_Backend.profilesetup.dto.ProfileDTO;
import com.example.Study_Hive_Backend.profilesetup.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173")
@RequestMapping("/api/matching")
public class MatchingController {

    private static final Logger logger = LoggerFactory.getLogger(MatchingController.class);

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private ProfileService profileService;

    @PostMapping("/find-partners")
    public ResponseEntity<?> findMatchingPartners(@RequestBody MatchingCriteria criteria, Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            logger.info("Finding partners for user: {}", userEmail);
            logger.info("Matching criteria: {}", criteria);

            ProfileDTO userProfile = profileService.getProfileByEmail(userEmail);
            if (userProfile == null) {
                logger.warn("User profile not found for email: {}", userEmail);
                return ResponseEntity.badRequest().body("User profile not found");
            }

            List<ProfileDTO> matchingPartners = matchingService.findMatchingPartners(userProfile, criteria);
            logger.info("Found {} matching partners", matchingPartners.size());

            return ResponseEntity.ok(matchingPartners);
        } catch (Exception e) {
            logger.error("Error finding matching partners", e);
            return ResponseEntity.internalServerError().body("An error occurred while finding matching partners");
        }
    }
}

