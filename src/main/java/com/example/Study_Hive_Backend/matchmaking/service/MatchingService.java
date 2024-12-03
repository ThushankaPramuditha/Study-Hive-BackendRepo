package com.example.Study_Hive_Backend.matchmaking.service;

import com.example.Study_Hive_Backend.matchmaking.dto.MatchingCriteria;
import com.example.Study_Hive_Backend.profilesetup.dto.ProfileDTO;
import com.example.Study_Hive_Backend.profilesetup.entity.Profile;
import com.example.Study_Hive_Backend.profilesetup.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchingService {

    @Autowired
    private ProfileRepository profileRepository;

    public List<ProfileDTO> findMatchingPartners(ProfileDTO userProfile, MatchingCriteria criteria) {
        List<Profile> allProfiles = profileRepository.findAll();
        return allProfiles.stream()
                .filter(profile -> profile.getUsername() != null && !profile.getUsername().equals(userProfile.getUsername()))
                .map(profile -> {
                    ProfileDTO matchedProfile = new ProfileDTO(profile);
                    int score = calculateMatchScore(userProfile, profile, criteria);
                    matchedProfile.setMatchScore(score);
                    matchedProfile.setMatchDetails(getMatchDetails(userProfile, profile, criteria));
                    return matchedProfile;
                })
                .filter(dto -> dto.getMatchScore() >= 30) // Lowered the threshold
                .sorted((a, b) -> Integer.compare(b.getMatchScore(), a.getMatchScore()))
                .collect(Collectors.toList());
    }

    private int calculateMatchScore(ProfileDTO userProfile, Profile profile, MatchingCriteria criteria) {
        int score = 0;

        if (matchesField(criteria.getStudyingFor(), profile.getStudyingFor())) {
            score += 35;
        }
        if (matchesField(criteria.getPreferredStudyTime(), profile.getPreferredStudyTime())) {
            score += 25;
        }
        if (matchesField(criteria.getAdaptability(), profile.getAdaptability())) {
            score += 20;
        }
        if (matchesField(userProfile.getUniversity(), profile.getUniversity())) {
            score += 10;
        }
        if (matchesField(criteria.getStudyGoal(), profile.getStudyGoal())) {
            score += 10;
        }

        return score;
    }

    private boolean matchesField(String value1, String value2) {
        return value1 != null && value2 != null && !value1.isEmpty() && !value2.isEmpty() &&
                value1.equalsIgnoreCase(value2);
    }

    private java.util.Map<String, String> getMatchDetails(ProfileDTO userProfile, Profile profile, MatchingCriteria criteria) {
        java.util.Map<String, String> details = new java.util.HashMap<>();

        if (matchesField(criteria.getStudyingFor(), profile.getStudyingFor())) {
            details.put("studyingFor", "Match");
        }
        if (matchesField(criteria.getPreferredStudyTime(), profile.getPreferredStudyTime())) {
            details.put("preferredStudyTime", "Match");
        }
        if (matchesField(criteria.getAdaptability(), profile.getAdaptability())) {
            details.put("adaptability", "Match");
        }
        if (matchesField(userProfile.getUniversity(), profile.getUniversity())) {
            details.put("university", "Match");
        }
        if (matchesField(criteria.getStudyGoal(), profile.getStudyGoal())) {
            details.put("studyGoal", "Match");
        }

        return details;
    }
}

