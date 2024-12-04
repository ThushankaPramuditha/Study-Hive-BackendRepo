//package com.example.Study_Hive_Backend.auth;
//
//import com.example.Study_Hive_Backend.config.JwtService;
//import com.example.Study_Hive_Backend.user.Role;
//import com.example.Study_Hive_Backend.user.Status;
//import com.example.Study_Hive_Backend.user.User;
//import com.example.Study_Hive_Backend.user.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//@Service
//@RequiredArgsConstructor
//public class AuthenticationService {
//    private final UserRepository repository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;
//
//    public AuthenticationResponse register(RegisterRequest request) {
//        if (repository.findByEmail(request.getEmail()).isPresent()) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
//        }
//
//        var user = User.builder()
//                .firstname(request.getFirstname())
//                .lastname(request.getLastname())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.USER)
//                .status(Status.INACTIVE) // Initially, the user is inactive
//                .build();
//        repository.save(user);
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }
//
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
//        var user = repository.findByEmail(request.getEmail())
//                .orElseThrow();
//        // Update status to ACTIVE
//        user.setStatus(Status.ACTIVE);
//        repository.save(user);
//
//
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }
//
//    public void logout(String email) {
//        var user = repository.findByEmail(email)
//                .orElseThrow();
//
//        // Update status to INACTIVE
//        user.setStatus(Status.INACTIVE);
//        repository.save(user);
//
//
//    }
//}

package com.example.Study_Hive_Backend.auth;

import com.example.Study_Hive_Backend.config.JwtService;
import com.example.Study_Hive_Backend.profilesetup.repository.ProfileRepository;
import com.example.Study_Hive_Backend.user.Role;
import com.example.Study_Hive_Backend.user.Status;
import com.example.Study_Hive_Backend.user.User;
import com.example.Study_Hive_Backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ProfileRepository ProfileRepository;
    private final UserRepository userRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        }

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .status(Status.INACTIVE) // Initially, the user is inactive
                .blocked(false)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Attempt to authenticate
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        // Fetch the user details from the database
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.getBlocked()) {
            throw new ResponseStatusException(HttpStatus.LOCKED, "Your account is blocked. Please contact support.");
        }

        // Check if the user profile exists
        boolean profileExists = ProfileRepository.findByUserId(user.getId()).isPresent();

        // Update user status to ACTIVE if they are not already active
        if (user.getStatus() != Status.ACTIVE) {
            user.setStatus(Status.ACTIVE);
            repository.save(user);
        }

        // Generate JWT token
        var jwtToken = jwtService.generateToken(user);

        // Build and return the response
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(user.getId())// Include user ID in the response
                .userFname(user.firstname())
                .userLname(user.lastname())
                .profileExists(profileExists)
                .build();
                
    }

    /**
     * Logs out a user by setting their status to INACTIVE.
     */
    public void logout(String email) {
        var user = repository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setStatus(Status.INACTIVE);
        repository.save(user);
    }


    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            // Extract email from the UserDetails object
            UserDetails userDetails = (UserDetails) principal;
            String email = userDetails.getUsername();  // In Spring Security, the username is typically the email

            // Retrieve user by email from the database (handles Optional)
            Optional<User> userOptional = userRepository.findByEmail(email);

            // If user is present, return the user, else return null or handle the case accordingly
            return userOptional.orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        }

        throw new RuntimeException("Authentication error: no principal found");
    }


    // public User getCurrentUser() {
    //     Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    //     if (principal instanceof UserDetails) {
    //         // Extract email from the UserDetails object
    //         UserDetails userDetails = (UserDetails) principal;
    //         String email = userDetails.getUsername();  // In Spring Security, the username is typically the email

    //         // Retrieve user by email from the database (handles Optional)
    //         Optional<User> userOptional = userRepository.findByEmail(email);

    //         // If user is present, return the user, else return null or handle the case accordingly
    //         return userOptional.orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    //     }

    //     throw new RuntimeException("Authentication error: no principal found");
    // }
}