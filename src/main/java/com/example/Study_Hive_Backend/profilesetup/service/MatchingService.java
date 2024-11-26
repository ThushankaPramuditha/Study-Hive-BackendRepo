package com.example.Study_Hive_Backend.profilesetup.service;

import com.example.Study_Hive_Backend.profilesetup.dto.ProfileDTO;
import com.example.Study_Hive_Backend.profilesetup.entity.Profile;
import com.example.Study_Hive_Backend.profilesetup.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchingService {

    @Autowired
    private ProfileRepository profileRepository;

    public List<ProfileDTO> findMatchingPartners(ProfileDTO profileDTO) {
        List<Profile> allProfiles = profileRepository.findAll();
        List<ProfileDTO> matchedProfiles = new ArrayList<>();

        for (Profile profile : allProfiles) {
            if (!profile.getUsername().equals(profileDTO.getUsername())) { // Exclude the requesting user's profile
                int matchScore = calculateMatchScore(profileDTO, profile);
                if (matchScore > 70) { // Assuming a threshold of 70 for a good match
                    matchedProfiles.add(convertToDTO(profile));
                }
            }
        }

        return matchedProfiles;
    }

    private int calculateMatchScore(ProfileDTO profileDTO, Profile profile) {
        int score = 0;

        if (profileDTO.getPreferredStudyTime().equals(profile.getPreferredStudyTime())) {
            score += 20;
        }
        if (profileDTO.getPreferredLanguage().equals(profile.getPreferredLanguages())) {
            score += 20;
        }
        if (profileDTO.getStudyGoal().equals(profile.getStudyGoal())) {
            score += 20;
        }
        if (profileDTO.getAdaptability().equals(profile.getAdaptability())) {
            score += 20;
        }
        if (profileDTO.getGender().equals(profile.getGender())) {
            score += 20;
        }

        return score;
    }

    private ProfileDTO convertToDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUsername(profile.getUsername());
        profileDTO.setGender(profile.getGender());
        profileDTO.setAdaptability(profile.getAdaptability());
        profileDTO.setPreferredLanguages(profile.getPreferredLanguages());
        profileDTO.setPreferredStudyTime(profile.getPreferredStudyTime());
        profileDTO.setStudyGoal(profile.getStudyGoal());
        profileDTO.setAboutMe(profile.getAboutMe());
        profileDTO.setStudyingFor(profile.getStudyingFor());
        profileDTO.setUniversity(profile.getUniversity());
        profileDTO.setProfilePhotoUrl(profile.getProfilePhotoUrl());
        return profileDTO;
    }
}