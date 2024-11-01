package com.example.Study_Hive_Backend.profilesetup.service;

import com.example.Study_Hive_Backend.profilesetup.dto.ProfileDTO;

import com.example.Study_Hive_Backend.profilesetup.entity.Profile;
import com.example.Study_Hive_Backend.profilesetup.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setUsername(profileDTO.getUsername());
        profile.setGender(profileDTO.getGender());
        profile.setAdaptability(profileDTO.getAdaptability());
        profile.setPreferredLanguages(profileDTO.getPreferredLanguage());
        profile.setPreferredStudyTime(profileDTO.getPreferredStudyTime());
        profile.setStudyGoal(profileDTO.getStudyGoal());
        profile.setAboutMe(profileDTO.getAboutMe());
        profile.setStudyingFor(profileDTO.getStudyingFor());
        profile.setUniversity(profileDTO.getUniversity());
        profile.setProfilePhotoUrl(profileDTO.getProfilePhotoUrl());

        profileRepository.save(profile);

        return profileDTO;  // Return the saved profile as a DTO
    }
}
