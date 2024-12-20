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
    ProfileDTO updateProfile(String loggedInEmail, ProfileDTO profileDTO);
    ProfileDTO getProfileByEmail(String email);
    ProfileDTO getProfileByUserId(Integer userId);
    List<ProfileDTO> findMatchingPartners(ProfileDTO profileDTO);


}