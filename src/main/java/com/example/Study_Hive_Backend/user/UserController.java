package com.example.Study_Hive_Backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        try {
            // Delete the user from the _user table
            userService.deleteUserById(userId);

            userService.deleteProfileByUserId(userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PutMapping("/{userId}/block")
    public ResponseEntity<String> blockUser(@PathVariable Integer userId, @RequestBody Map<String, Boolean> blockRequest) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();
        Boolean block = blockRequest.get("blocked");

        if (block == null) {
            return ResponseEntity.badRequest().body("Block status is required");
        }

        user.setBlocked(block);  // Set the blocked status to true or false

        userRepository.save(user);
        return ResponseEntity.ok(block ? "User blocked" : "User unblocked");
    }

    @GetMapping("/{userId}/blockCount")
    public ResponseEntity<Integer> getBlockCount(@PathVariable Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(optionalUser.get().getBlockCount());
    }


}
