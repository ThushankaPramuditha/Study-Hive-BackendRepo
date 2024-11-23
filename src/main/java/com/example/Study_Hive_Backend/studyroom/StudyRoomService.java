package com.example.Study_Hive_Backend.studyroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudyRoomService {

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    public StudyRoomEntity createStudyRoom(StudyRoomEntity studyRoom) {
        return studyRoomRepository.save(studyRoom);
    }

    public Optional<StudyRoomEntity> getStudyRoom(Long id) {
        return studyRoomRepository.findById(id);
    }
}
