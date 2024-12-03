//package com.example.Study_Hive_Backend.profilesetup.service;
//
//import com.example.Study_Hive_Backend.profilesetup.dto.ProfileDTO;
//import com.example.Study_Hive_Backend.profilesetup.entity.Profile;
//import com.example.Study_Hive_Backend.profilesetup.repository.ProfileRepository;
//import com.example.Study_Hive_Backend.user.User;
//import com.example.Study_Hive_Backend.user.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import java.util.Objects;
//import java.util.Optional;
//
//
//@Service
//public class ProfileServiceImpl implements ProfileService {
//
//    @Autowired
//    private ProfileRepository profileRepository;
//    private final MatchingService matchingService;
//
//    public ProfileServiceImpl(ProfileRepository profileRepository, MatchingService matchingService) {
//        this.profileRepository = profileRepository;
//        this.matchingService = matchingService;
//    }
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @Override
//    public ProfileDTO createProfile(ProfileDTO profileDTO) {
//
//        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // Assuming email is used for login
//
//        // Fetch the logged-in user by email
//        User loggedInUser = userRepository.findByEmail(loggedInEmail)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Profile profile = new Profile();
//        profile.setUsername(profileDTO.getUsername());
//        profile.setGender(profileDTO.getGender());
//        profile.setAdaptability(profileDTO.getAdaptability());
//        profile.setPreferredLanguages(profileDTO.getPreferredLanguages());
//        profile.setPreferredStudyTime(profileDTO.getPreferredStudyTime());
//        profile.setStudyGoal(profileDTO.getStudyGoal());
//        profile.setAboutMe(profileDTO.getAboutMe());
//        profile.setStudyingFor(profileDTO.getStudyingFor());
//        profile.setUniversity(profileDTO.getUniversity());
////        profile.setProfilePhotoUrl(profileDTO.getProfilePhotoUrl());
//
//        if (profile.getProfilePhotoUrl() != null && profile.getProfilePhotoUrl().length() > 2048) {
////            logger.warn("Profile photo URL exceeds maximum length, truncating.");
//            profile.setProfilePhotoUrl(profile.getProfilePhotoUrl().substring(0, 2048));
//        }
//
//        profile.setUser(loggedInUser);
//
//        profileRepository.save(profile);
//
//        return profileDTO;
//    }
//
//    @Override
//    public ProfileDTO updateProfile(String loggedInEmail, ProfileDTO profileDTO) {
//        // Find the user based on their email
//        User user = userRepository.findByEmail(loggedInEmail)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Find the user's profile based on the user ID
//        Profile profile = profileRepository.findByUserId(user.getId())
//                .orElseThrow(() -> new RuntimeException("Profile not found"));
//
//        // Update profile fields
//        profile.setUsername(profileDTO.getUsername());
//        profile.setGender(profileDTO.getGender());
//        profile.setUniversity(profileDTO.getUniversity());
//        profile.setStudyingFor(profileDTO.getStudyingFor());
//        profile.setAdaptability(profileDTO.getAdaptability());
//        profile.setPreferredLanguages(profileDTO.getPreferredLanguages());
//        profile.setStudyGoal(profileDTO.getStudyGoal());
//        profile.setAboutMe(profileDTO.getAboutMe());
//        profile.setPhoneNumber(profileDTO.getPhoneNumber());
//        profile.setEmergencyContact(profileDTO.getEmergencyContact());
//        ;
//
//        // Save the updated profile
//        Profile updatedProfile = profileRepository.save(profile);
//
//        // Convert the updated profile to a DTO and return it
//        return new ProfileDTO(updatedProfile);
//    }
//    @Override
//    public ProfileDTO getProfileByEmail(String email) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> {
//                    return new RuntimeException("User not found for email: " + email);
//                });
//
//        Profile profile = profileRepository.findByUserId(user.getId())
//                .orElseThrow(() -> {
//
//                    return new RuntimeException("Profile not found for user with email: " + email);
//                });
//
//        return new ProfileDTO(profile);
//    }
//
//
//
//    @Override
//    public ProfileDTO getProfileOfLoggedInUser() {
//        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // Get the logged-in user's email
//
//        // Fetch the logged-in user
//        User loggedInUser = userRepository.findByEmail(loggedInEmail)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Fetch the profile using the logged-in user's ID
//        Profile profile = profileRepository.findByUserId(loggedInUser.getId())
//                .orElseThrow(() -> new RuntimeException("Profile not found"));
//
//        // Convert Profile entity to ProfileDTO (if necessary)
//        ProfileDTO profileDTO = new ProfileDTO();
//        profileDTO.setUserId(profile.getUser().getId());
//        profileDTO.setUsername(profile.getUsername());
//        profileDTO.setGender(profile.getGender());
//        profileDTO.setAdaptability(profile.getAdaptability());
//        profileDTO.setPreferredLanguages(profile.getPreferredLanguages());
//        profileDTO.setPreferredStudyTime(profile.getPreferredStudyTime());
//        profileDTO.setStudyGoal(profile.getStudyGoal());
//        profileDTO.setAboutMe(profile.getAboutMe());
//        profileDTO.setStudyingFor(profile.getStudyingFor());
//        profileDTO.setUniversity(profile.getUniversity());
//        profileDTO.setProfilePhotoUrl(profile.getProfilePhotoUrl());
//
//        return profileDTO;
//    }
//
//
//}
//
//


package com.example.Study_Hive_Backend.profilesetup.service;

import com.example.Study_Hive_Backend.profilesetup.dto.ProfileDTO;
import com.example.Study_Hive_Backend.profilesetup.entity.Profile;
import com.example.Study_Hive_Backend.profilesetup.repository.ProfileRepository;
import com.example.Study_Hive_Backend.user.User;
import com.example.Study_Hive_Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
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

        if (profileDTO.getProfilePhotoUrl() != null && profileDTO.getProfilePhotoUrl().length() > 2048) {
            profile.setProfilePhotoUrl(profileDTO.getProfilePhotoUrl().substring(0, 2048));
        } else {
            profile.setProfilePhotoUrl(profileDTO.getProfilePhotoUrl());
        }

        profile.setUser(loggedInUser);
        Profile savedProfile = profileRepository.save(profile);
        return new ProfileDTO(savedProfile);
    }

    @Override
    public List<ProfileDTO> findMatchingPartners(ProfileDTO profileDTO) {
        return null;
    }

    @Override
    public ProfileDTO updateProfile(String loggedInEmail, ProfileDTO profileDTO) {
        User user = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

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

        Profile updatedProfile = profileRepository.save(profile);
        return new ProfileDTO(updatedProfile);
    }

    @Override
    public ProfileDTO getProfileByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found for email: " + email));

        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Profile not found for user with email: " + email));

        return new ProfileDTO(profile);
    }

    @Override
    public ProfileDTO getProfileOfLoggedInUser() {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = profileRepository.findByUserId(loggedInUser.getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return new ProfileDTO(profile);
    }
}

