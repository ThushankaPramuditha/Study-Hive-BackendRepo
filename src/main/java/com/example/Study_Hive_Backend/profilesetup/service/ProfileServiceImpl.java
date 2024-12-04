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
//        profile.setProfilePhotoUrl(profileDTO.getProfilePhotoUrl());

        if (profileDTO.getProfilePhotoUrl() != null && profileDTO.getProfilePhotoUrl().length() > 2048) {
            profile.setProfilePhotoUrl(profileDTO.getProfilePhotoUrl().substring(0, 2048));
        } else {
            profile.setProfilePhotoUrl(profileDTO.getProfilePhotoUrl());
        }


        profile.setUser(loggedInUser);

        profileRepository.save(profile);

        return profileDTO;
    }

//    @Override
//    public Profile updateProfile(Long id, Profile updatedProfile) {
//        Optional<Profile> profileOpt = profileRepository.findById(id);
//
//        if (profileOpt.isPresent()) {
//            Profile profile = profileOpt.get();
//
//            // Only update fields that are not null in updatedProfile
//            if (updatedProfile.getUsername() != null) {
//                profile.setUsername(updatedProfile.getUsername());
//            }
//            if (updatedProfile.getEmail() != null) {
//                profile.setEmail(updatedProfile.getEmail());
//            }
//            if (updatedProfile.getPhoneNumber() != null) {
//                profile.setPhoneNumber(updatedProfile.getPhoneNumber());
//            }
//            if (updatedProfile.getEmergencyContact() != null) {
//                profile.setEmergencyContact(updatedProfile.getEmergencyContact());
//            }
//            if (updatedProfile.getGender() != null) {
//                profile.setGender(updatedProfile.getGender());
//            }
//            if (updatedProfile.getAdaptability() != null) {
//                profile.setAdaptability(updatedProfile.getAdaptability());
//            }
//            if (updatedProfile.getPreferredLanguages() != null) {
//                profile.setPreferredLanguages(updatedProfile.getPreferredLanguages());
//            }
//            if (updatedProfile.getPreferredStudyTime() != null) {
//                profile.setPreferredStudyTime(updatedProfile.getPreferredStudyTime());
//            }
//            if (updatedProfile.getStudyGoal() != null) {
//                profile.setStudyGoal(updatedProfile.getStudyGoal());
//            }
//            if (updatedProfile.getAboutMe() != null) {
//                profile.setAboutMe(updatedProfile.getAboutMe());
//            }
//            if (updatedProfile.getStudyingFor() != null) {
//                profile.setStudyingFor(updatedProfile.getStudyingFor());
//            }
//            if (updatedProfile.getUniversity() != null) {
//                profile.setUniversity(updatedProfile.getUniversity());
//            }
//            if (updatedProfile.getProfilePhotoUrl() != null) {
//                profile.setProfilePhotoUrl(updatedProfile.getProfilePhotoUrl());
//            }
//
//            return profileRepository.save(profile);
//        }
//
//
//        throw new RuntimeException("Profile not found");
//    }

//    @Override
//    public Profile updateProfile(Integer userId, Profile updatedProfile) {
//        // Fetch the profile by user_id
//        Optional<Profile> optionalProfile = profileRepository.findByUserId(userId);
//        if (optionalProfile.isEmpty()) {
//            throw new RuntimeException("Profile not found for user ID: " + userId);
//        }
//
//        // Get the profile from the Optional
//        Profile profile = optionalProfile.get();
//
//        // Update the fields with the new values
//        profile.setUsername(updatedProfile.getUsername());
//        profile.setEmail(updatedProfile.getEmail());
//        profile.setPhoneNumber(updatedProfile.getPhoneNumber());
//        profile.setEmergencyContact(updatedProfile.getEmergencyContact());
//        profile.setGender(updatedProfile.getGender());
//        profile.setAdaptability(updatedProfile.getAdaptability());
//        profile.setPreferredLanguages(updatedProfile.getPreferredLanguages());
//        profile.setPreferredStudyTime(updatedProfile.getPreferredStudyTime());
//        profile.setStudyGoal(updatedProfile.getStudyGoal());
//        profile.setAboutMe(updatedProfile.getAboutMe());
//        profile.setStudyingFor(updatedProfile.getStudyingFor());
//        profile.setUniversity(updatedProfile.getUniversity());
//        profile.setProfilePhotoUrl(updatedProfile.getProfilePhotoUrl());
//        return profileRepository.save(profile);
//    }

    @Override
    public ProfileDTO updateProfile(String loggedInEmail, ProfileDTO profileDTO) {
        // Find the user based on their email
        User user = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find the user's profile based on the user ID
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        // Update profile fields
        profile.setUsername(profileDTO.getUsername());
        profile.setGender(profileDTO.getGender());
        profile.setUniversity(profileDTO.getUniversity());
        profile.setStudyingFor(profileDTO.getStudyingFor());
        profile.setAdaptability(profileDTO.getAdaptability());
        profile.setPreferredLanguages(profileDTO.getPreferredLanguages());
        profile.setStudyGoal(profileDTO.getStudyGoal());
        profile.setAboutMe(profileDTO.getAboutMe());
        profile.setPhoneNumber(profileDTO.getPhoneNumber());
        profile.setEmergencyContact(profileDTO.getEmergencyContact());
        ;

        // Save the updated profile
        Profile updatedProfile = profileRepository.save(profile);

        // Convert the updated profile to a DTO and return it
        return new ProfileDTO(updatedProfile);
    }

    @Override
    public ProfileDTO getProfileOfLoggedInUser() {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // Get the logged-in user's email

        // Fetch the logged-in user
        User loggedInUser = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the profile using the logged-in user's ID
        Profile profile = profileRepository.findByUserId(loggedInUser.getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        // Convert Profile entity to ProfileDTO (if necessary)
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUserId(profile.getUser().getId());
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


    @Override
    public ProfileDTO getProfileByUserId(Integer userId) {
        // Fetch the profile by user ID
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found for user ID: " + userId));

        // Convert the Profile entity to a DTO
        return new ProfileDTO(profile);
    }


}


