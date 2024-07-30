package com.example.Study_Hive_Backend.profilesetup.service;


import com.example.Study_Hive_Backend.profilesetup.dto.ProfileDTO;
import com.example.Study_Hive_Backend.profilesetup.entity.Profile;
import com.example.Study_Hive_Backend.profilesetup.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Profile createProfile(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setFirstName(profileDTO.getFirstName());
        profile.setLastName(profileDTO.getLastName());
        profile.setGender(profileDTO.getGender());
        profile.setUniversity(profileDTO.getUniversity());
        profile.setStudyingFor(profileDTO.getStudyingFor());
        profile.setLevelOfKnowledge(profileDTO.getLevelOfKnowledge());
        profile.setPreferredLanguage(profileDTO.getPreferredLanguage());
        profile.setPreferredStudyTime(profileDTO.getPreferredStudyTime());
        profile.setStudyGoal(profileDTO.getStudyGoal());
        profile.setAboutMe(profileDTO.getAboutMe());
        profile.setProfilePhotoUrl(profileDTO.getProfilePhotoUrl());
        return profileRepository.save(profile);
    }

    @Override
    public Profile updateProfile(Long id, ProfileDTO profileDTO) {
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.setFirstName(profileDTO.getFirstName());
        profile.setLastName(profileDTO.getLastName());
        profile.setGender(profileDTO.getGender());
        profile.setUniversity(profileDTO.getUniversity());
        profile.setStudyingFor(profileDTO.getStudyingFor());
        profile.setLevelOfKnowledge(profileDTO.getLevelOfKnowledge());
        profile.setPreferredLanguage(profileDTO.getPreferredLanguage());
        profile.setPreferredStudyTime(profileDTO.getPreferredStudyTime());
        profile.setStudyGoal(profileDTO.getStudyGoal());
        profile.setAboutMe(profileDTO.getAboutMe());
        profile.setProfilePhotoUrl(profileDTO.getProfilePhotoUrl());
        return profileRepository.save(profile);
    }

    @Override
    public Profile getProfileById(Long id) {
        return profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    @Override
    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }
}