package com.example.Study_Hive_Backend.user;


import com.example.Study_Hive_Backend.profilesetup.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email); // Fetch user by email
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }


    public void deleteUserById(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    // Method to delete a profile by the user's ID
    public void deleteProfileByUserId(Integer userId) {
        if (profileRepository.existsByUserId(userId)) {
            profileRepository.deleteByUserId(userId);
        }
    }

}