package com.example.Study_Hive_Backend.profilesetup.service;

import com.example.Study_Hive_Backend.profilesetup.dto.ProfileDTO;
import com.example.Study_Hive_Backend.profilesetup.entity.Profile;
import com.example.Study_Hive_Backend.profilesetup.repository.ProfileRepository;
import com.example.Study_Hive_Backend.user.User;
import com.example.Study_Hive_Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public ProfileDTO createProfile(ProfileDTO profileDTO) {

        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // Assuming email is used for login

        // Fetch the logged-in user by email
        User loggedInUser = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = new Profile();
        profile.setUsername(profileDTO.getUsername());
        profile.setGender(profileDTO.getGender());
        profile.setAdaptability(profileDTO.getAdaptability());
        profile.setPreferredLanguages(profileDTO.getPreferredLanguages());
        profile.setPreferredStudyTime(profileDTO.getPreferredStudyTime());
        profile.setStudyGoal(profileDTO.getStudyGoal());
        profile.setAboutMe(profileDTO.getAboutMe());
        profile.setStudyingFor(profileDTO.getStudyingFor());
        profile.setUniversity(profileDTO.getUniversity());
        profile.setProfilePhotoUrl(profileDTO.getProfilePhotoUrl());

        profile.setUser(loggedInUser);

        profileRepository.save(profile);

        return profileDTO;
    }

    @Override
    public Profile updateProfile(Long id, Profile updatedProfile) {
        Optional<Profile> profileOpt = profileRepository.findById(id);

        if (profileOpt.isPresent()) {
            Profile profile = profileOpt.get();

            // Only update fields that are not null in updatedProfile
            if (updatedProfile.getUsername() != null) {
                profile.setUsername(updatedProfile.getUsername());
            }
            if (updatedProfile.getEmail() != null) {
                profile.setEmail(updatedProfile.getEmail());
            }
            if (updatedProfile.getPhoneNumber() != null) {
                profile.setPhoneNumber(updatedProfile.getPhoneNumber());
            }
            if (updatedProfile.getEmergencyContact() != null) {
                profile.setEmergencyContact(updatedProfile.getEmergencyContact());
            }
            if (updatedProfile.getGender() != null) {
                profile.setGender(updatedProfile.getGender());
            }
            if (updatedProfile.getAdaptability() != null) {
                profile.setAdaptability(updatedProfile.getAdaptability());
            }
            if (updatedProfile.getPreferredLanguages() != null) {
                profile.setPreferredLanguages(updatedProfile.getPreferredLanguages());
            }
            if (updatedProfile.getPreferredStudyTime() != null) {
                profile.setPreferredStudyTime(updatedProfile.getPreferredStudyTime());
            }
            if (updatedProfile.getStudyGoal() != null) {
                profile.setStudyGoal(updatedProfile.getStudyGoal());
            }
            if (updatedProfile.getAboutMe() != null) {
                profile.setAboutMe(updatedProfile.getAboutMe());
            }
            if (updatedProfile.getStudyingFor() != null) {
                profile.setStudyingFor(updatedProfile.getStudyingFor());
            }
            if (updatedProfile.getUniversity() != null) {
                profile.setUniversity(updatedProfile.getUniversity());
            }
            if (updatedProfile.getProfilePhotoUrl() != null) {
                profile.setProfilePhotoUrl(updatedProfile.getProfilePhotoUrl());
            }

            return profileRepository.save(profile);
        }

        throw new RuntimeException("Profile not found");
    }
}


