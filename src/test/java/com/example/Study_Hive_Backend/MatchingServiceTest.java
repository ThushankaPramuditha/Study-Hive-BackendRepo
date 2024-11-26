package com.example.Study_Hive_Backend;

import com.example.Study_Hive_Backend.profilesetup.dto.ProfileDTO;
import com.example.Study_Hive_Backend.profilesetup.service.ProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MatchingServiceTest {

    @Autowired
    private ProfileServiceImpl profileService;

    @Test
    public void testFindMatchingPartners() {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUsername("john_doe");
        profileDTO.setGender("Male");
        profileDTO.setAdaptability("High");
        profileDTO.setPreferredLanguages("English");
        profileDTO.setPreferredStudyTime("Morning");
        profileDTO.setStudyGoal("Pass exams");
        profileDTO.setAboutMe("I am a dedicated student.");
        profileDTO.setStudyingFor("Computer Science");
        profileDTO.setUniversity("XYZ University");
        profileDTO.setProfilePhotoUrl("http://example.com/photo.jpg");

        List<ProfileDTO> matchingPartners = profileService.findMatchingPartners(profileDTO);
        assertNotNull(matchingPartners);
    }
}