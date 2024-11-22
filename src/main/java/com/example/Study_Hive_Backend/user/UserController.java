package com.example.Study_Hive_Backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @PostMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Validate Gmail format
        if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            return ResponseEntity.badRequest().body(Map.of("isValid", false, "isRegistered", false));
        }

        // Check if email exists
        boolean isRegistered = userRepository.existsByEmail(email.toLowerCase());
        return ResponseEntity.ok(Map.of("isValid", true, "isRegistered", isRegistered));
    }
}
