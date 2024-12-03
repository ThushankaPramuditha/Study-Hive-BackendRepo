package com.example.Study_Hive_Backend.studyroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/studyrooms")
public class StudyRoomController {

    @Autowired
    private StudyRoomService studyRoomService;
    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @PostMapping("/create")
    public ResponseEntity<StudyRoomEntity> createStudyRoom(@RequestBody StudyRoomEntity studyRoom) {
        StudyRoomEntity newRoom = studyRoomService.createStudyRoom(studyRoom);
        System.out.println("Created Room: " + newRoom); // Add this to log the created room
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyRoomEntity> getStudyRoom(@PathVariable Long id) {
        Optional<StudyRoomEntity> room = studyRoomService.getStudyRoom(id);
        return room.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<StudyRoomEntity> getAllStudyRooms() {
        List<StudyRoomEntity> allRooms = studyRoomRepository.findAll();
        System.out.println("All Study Rooms: " + allRooms); // Log the fetched rooms
        return allRooms;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudyRoom(@PathVariable Long id) {
        System.out.println("Attempting to delete study room with ID: " + id); // Log request
        try {
            boolean isDeleted = studyRoomService.deleteStudyRoom(id);
            if (isDeleted) {
                return ResponseEntity.ok("Study room deleted successfully.");
            } else {
                return ResponseEntity.status(404).body("Study room not found.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage()); // Log error details
            return ResponseEntity.status(500).body("Error deleting the study room: " + e.getMessage());
        }
    }

}


