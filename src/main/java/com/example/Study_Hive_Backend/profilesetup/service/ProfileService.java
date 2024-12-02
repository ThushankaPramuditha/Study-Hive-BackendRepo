package com.example.Study_Hive_Backend.profilesetup.service;

import com.example.Study_Hive_Backend.profilesetup.dto.ProfileDTO;
import com.example.Study_Hive_Backend.profilesetup.entity.Profile;
import com.example.Study_Hive_Backend.profilesetup.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProfileService {
    ProfileDTO createProfile(ProfileDTO profileDTO);
    ProfileDTO getProfileOfLoggedInUser();
   // Profile updateProfile(Long id, Profile updatedProfile);
//   Profile updateProfile(Integer userId, Profile updatedProfile);
   ProfileDTO updateProfile(String loggedInEmail, ProfileDTO profileDTO);
    ProfileDTO getProfileByUserId(Integer userId);


}