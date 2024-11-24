package com.example.Study_Hive_Backend.profilesetup.controller;

import com.example.Study_Hive_Backend.profilesetup.dto.ProfileDTO;
import com.example.Study_Hive_Backend.profilesetup.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping
    public ResponseEntity<ProfileDTO> createProfile(@RequestBody ProfileDTO profileDTO) {
        ProfileDTO savedProfile = profileService.createProfile(profileDTO);
        return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
    }
    @PostMapping("/match")
    public List<ProfileDTO> findMatchingPartners(@RequestBody ProfileDTO profileDTO) {
        return profileService.findMatchingPartners(profileDTO);
    }
}
