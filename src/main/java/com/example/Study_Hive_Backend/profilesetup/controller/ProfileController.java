package com.example.Study_Hive_Backend.profilesetup.controller;

import com.example.Study_Hive_Backend.profilesetup.dto.ProfileDTO;
import com.example.Study_Hive_Backend.profilesetup.entity.Profile;
import com.example.Study_Hive_Backend.profilesetup.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {



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

    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long id, @RequestBody Profile updatedProfile) {
        Profile profile = profileService.updateProfile(id, updatedProfile);
        return ResponseEntity.ok(profile);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Profile> partiallyUpdateProfile(@PathVariable Long id, @RequestBody Profile partialUpdate) {
        Profile updatedProfile = profileService.updateProfile(id, partialUpdate);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileDTO> getLoggedInUserProfile() {
        ProfileDTO profileDTO = profileService.getProfileOfLoggedInUser();
        return ResponseEntity.ok(profileDTO);
    }






}
