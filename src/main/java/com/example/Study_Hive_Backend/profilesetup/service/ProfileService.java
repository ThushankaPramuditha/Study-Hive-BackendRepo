package com.example.Study_Hive_Backend.profilesetup.service;


import com.example.Study_Hive_Backend.profilesetup.dto.ProfileDTO;
import com.example.Study_Hive_Backend.profilesetup.entity.Profile;

import java.util.List;

public interface ProfileService {
    Profile createProfile(ProfileDTO profileDTO);
    Profile updateProfile(Long id, ProfileDTO profileDTO);
    Profile getProfileById(Long id);
    void deleteProfile(Long id);
    List<Profile> getAllProfiles();
}