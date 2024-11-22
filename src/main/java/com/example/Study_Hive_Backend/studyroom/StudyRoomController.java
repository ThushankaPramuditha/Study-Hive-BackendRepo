package com.example.Study_Hive_Backend.studyroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/studyrooms")
public class StudyRoomController {

    @Autowired
    private StudyRoomService studyRoomService;

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
}


