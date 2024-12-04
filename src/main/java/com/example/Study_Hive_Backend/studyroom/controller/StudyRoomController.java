//package com.example.Study_Hive_Backend.studyroom.controller;
//
//import com.example.Study_Hive_Backend.studyroom.entity.StudyRoomEntity;
//import com.example.Study_Hive_Backend.studyroom.service.StudyRoomService;
//import com.example.Study_Hive_Backend.studyroom.entity.StudyRoomParticipantEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/studyrooms")
//public class StudyRoomController {
//
//    @Autowired
//    private StudyRoomService studyRoomService;
//
//    @PostMapping("/create")
//    public ResponseEntity<?> createStudyRoom(@RequestBody StudyRoomEntity studyRoom) {
//        try {
//            StudyRoomEntity newRoom = studyRoomService.createStudyRoom(studyRoom);
//            System.out.println("Created Room: " + newRoom);
//            return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error creating study room: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping
//    public ResponseEntity<?> getAllStudyRooms() {
//        try {
//            return new ResponseEntity<>(studyRoomService.getAllStudyRooms(), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error retrieving study rooms: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getStudyRoom(@PathVariable Long id) {
//        Optional<StudyRoomEntity> room = studyRoomService.getStudyRoom(id);
//        if (room.isPresent()) {
//            return ResponseEntity.ok(room.get());
//        } else {
//            return new ResponseEntity<>("Study room not found with ID: " + id, HttpStatus.NOT_FOUND);
//        }
//
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteStudyRoom(@PathVariable Long id) {
//        try {
//            boolean isDeleted = studyRoomService.deleteStudyRoom(id);
//            if (isDeleted) {
//                return new ResponseEntity<>("Study room deleted successfully", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Study room not found with ID: " + id, HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error deleting study room: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PostMapping("/{studyRoomId}/join-request")
//    public ResponseEntity<?> sendJoinRequest(@PathVariable Long studyRoomId, @RequestParam Integer userId) {
//        try {
//            StudyRoomParticipantEntity request = studyRoomService.sendJoinRequest(studyRoomId, userId);
//            return new ResponseEntity<>(request, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error sending join request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PostMapping("/join-request/{requestId}/accept")
//    public ResponseEntity<?> acceptJoinRequest(@PathVariable Long requestId) {
//        try {
//            StudyRoomParticipantEntity acceptedRequest = studyRoomService.acceptJoinRequest(requestId);
//            return ResponseEntity.ok(acceptedRequest);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error accepting join request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//
//}
//
//

package com.example.Study_Hive_Backend.studyroom.controller;

import com.example.Study_Hive_Backend.studyroom.entity.StudyRoomEntity;
import com.example.Study_Hive_Backend.studyroom.entity.StudyRoomParticipantEntity;
import com.example.Study_Hive_Backend.studyroom.service.StudyRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/studyrooms")
public class StudyRoomController {

    @Autowired
    private StudyRoomService studyRoomService;

    @PostMapping("/create")
    public ResponseEntity<?> createStudyRoom(@RequestBody StudyRoomEntity studyRoom) {
        try {
            StudyRoomEntity newRoom = studyRoomService.createStudyRoom(studyRoom);
            return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating study room: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllStudyRooms() {
        try {
            List<StudyRoomEntity> studyRooms = studyRoomService.getAllStudyRooms();
            return new ResponseEntity<>(studyRooms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving study rooms: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudyRoom(@PathVariable Long id) {
        try {
            Optional<StudyRoomEntity> studyRoom = studyRoomService.getStudyRoom(id);
            return ResponseEntity.ok(studyRoom);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Study room not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudyRoom(@PathVariable Long id) {
        try {
            boolean deleted = studyRoomService.deleteStudyRoom(id);
            if (deleted) {
                return new ResponseEntity<>("Study room deleted successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Study room not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting study room: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{studyRoomId}/join-request")
    public ResponseEntity<?> sendJoinRequest(@PathVariable Long studyRoomId, @RequestParam Integer userId) {
        try {
            StudyRoomParticipantEntity participantDTO = studyRoomService.sendJoinRequest(studyRoomId, userId);
            return new ResponseEntity<>(participantDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error sending join request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/join-request/{requestId}/accept")
//    public ResponseEntity<?> acceptJoinRequest(@PathVariable Long requestId) {
//        try {
//            StudyRoomParticipantEntity acceptedRequest = studyRoomService.acceptJoinRequest(requestId);
//            return ResponseEntity.ok(acceptedRequest);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>("Error accepting join request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/{studyRoomId}/join")
    public ResponseEntity<?> joinStudyRoom(@PathVariable Long studyRoomId,
                                           @RequestParam Integer userId,
                                           @RequestParam String roomKey,
                                           @RequestParam boolean acceptTerms) {
        try {
            // Call the service method to join the room
            StudyRoomParticipantEntity participant = studyRoomService.joinStudyRoom(studyRoomId, userId, roomKey, acceptTerms);
            return new ResponseEntity<>(participant, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error joining study room: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    public List<StudyRoomEntity> getStudyRoomsByUserId(@PathVariable Integer userId) {
        return studyRoomService.getStudyRoomsByUserId(userId);
    }
//    @GetMapping("/{roomId}/accepted-users")
//    public ResponseEntity<?> getAcceptedUsers(@PathVariable Long roomId) {
//        try {
//            List<StudyRoomParticipantEntity> acceptedUsers = studyRoomService.getAcceptedUsers(roomId);
//            return new ResponseEntity<>(acceptedUsers, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error retrieving accepted users: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
@GetMapping("/{roomId}/accepted-users")
public ResponseEntity<?> getAcceptedUsers(@PathVariable Long roomId, @RequestHeader("Authorization") String authHeader) {
    try {
        // Extract the JWT token from the Authorization header
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        // Call the updated service method with roomId and token
        List<Map<String, Object>> acceptedUsers = studyRoomService.getAcceptedUsers(roomId, token);
        return new ResponseEntity<>(acceptedUsers, HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>("Error retrieving accepted users: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @PostMapping("/{roomId}/leave")
    public ResponseEntity<?> leaveRoom(@PathVariable Long roomId, @RequestParam Integer userId, @RequestParam Long timeSpent) {
        try {
            studyRoomService.leaveRoom(roomId, userId, timeSpent);
            return new ResponseEntity<>("Successfully left the room", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error leaving room: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

