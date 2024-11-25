package com.example.Study_Hive_Backend.profilesetup.controller;

import com.example.Study_Hive_Backend.profilesetup.dto.ProfileDTO;
import com.example.Study_Hive_Backend.profilesetup.entity.Profile;
import com.example.Study_Hive_Backend.profilesetup.repository.ProfileRepository;
import com.example.Study_Hive_Backend.profilesetup.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {


    @Autowired
    private ProfileRepository ProfileRepository;


    @Autowired
    private ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @PostMapping()
    public ResponseEntity<ProfileDTO> createProfile(@RequestBody ProfileDTO profileDTO) {
        ProfileDTO savedProfile = profileService.createProfile(profileDTO);
        return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
    }

//    @PutMapping("/api/profiles/{id}")
//    public ResponseEntity<?> updateProfile(@PathVariable("userId") Integer userId, @RequestBody Profile updatedProfile) {
//        if (userId == null || userId <= 0) {
//            return ResponseEntity.badRequest().body("Invalid userId");
//        }
//
//        try {
//            Profile profile = profileService.updateProfile(userId, updatedProfile);
//            return ResponseEntity.ok(profile);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating profile");
//        }
//    }

    @PutMapping("/me")
    public ResponseEntity<ProfileDTO> updateProfile(@RequestBody ProfileDTO profileDTO, Authentication authentication) {
        String loggedInEmail = authentication.getName(); // Get the logged-in user's email
        ProfileDTO updatedProfile = profileService.updateProfile(loggedInEmail, profileDTO);
        return ResponseEntity.ok(updatedProfile);
    }


    @GetMapping("/me")
    public ResponseEntity<ProfileDTO> getLoggedInUserProfile() {
        ProfileDTO profileDTO = profileService.getProfileOfLoggedInUser();
        return ResponseEntity.ok(profileDTO);
    }

    @GetMapping("/exists/{userId}")
    public ResponseEntity<Boolean> checkProfileExists(@PathVariable Integer userId) {
        boolean exists = ProfileRepository.existsByUserId(userId);
        return ResponseEntity.ok(exists);
    }





}
