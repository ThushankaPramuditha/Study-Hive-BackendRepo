package com.example.Study_Hive_Backend.studyroom.service;

import com.example.Study_Hive_Backend.studyroom.entity.StudyRoomEntity;
import com.example.Study_Hive_Backend.studyroom.entity.StudyRoomParticipantEntity;
import com.example.Study_Hive_Backend.studyroom.repository.StudyRoomParticipantRepository;
import com.example.Study_Hive_Backend.studyroom.repository.StudyRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;
    private final StudyRoomParticipantRepository studyRoomParticipantRepository;


    @Autowired
    public StudyRoomService(StudyRoomRepository studyRoomRepository, StudyRoomParticipantRepository studyRoomParticipantRepository) {
        this.studyRoomRepository = studyRoomRepository;
        this.studyRoomParticipantRepository = studyRoomParticipantRepository;
    }


    public StudyRoomEntity createStudyRoom(StudyRoomEntity studyRoom) {
        if(studyRoom.getRoomName() == null || studyRoom.getRoomName().isEmpty()) {
            throw new IllegalArgumentException("Room name cannot be empty.");
        }
        return studyRoomRepository.save(studyRoom);
    }
    public Optional<StudyRoomEntity> getStudyRoom(Long id) {
        return studyRoomRepository.findById(id);
    }

    public List<StudyRoomEntity> getAllStudyRooms() {
        return studyRoomRepository.findAll();
    }

    public boolean deleteStudyRoom(Long id) {
        if (studyRoomRepository.existsById(id)) {
            studyRoomRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public StudyRoomParticipantEntity sendJoinRequest(Long studyRoomId, Integer userId) {
        Optional<StudyRoomEntity> optionalRoom = studyRoomRepository.findById(studyRoomId);
        if (optionalRoom.isEmpty()) {
            throw new IllegalArgumentException("Study room not found with ID: " + studyRoomId);
        }

        StudyRoomEntity studyRoom = optionalRoom.get();
        boolean alreadyParticipant = studyRoom.getParticipants().stream()
                .anyMatch(participant -> participant.getUserId().equals(userId));

        if (alreadyParticipant) {
            throw new IllegalArgumentException("User is already a participant in the study room.");
        }

        StudyRoomParticipantEntity participant = new StudyRoomParticipantEntity();
        participant.setStudyRoom(studyRoom);
        participant.setUserId(userId);
        participant.setStatus(StudyRoomParticipantEntity.ParticipantStatus.PENDING);

        return studyRoomParticipantRepository.save(participant);
    }

    public StudyRoomParticipantEntity acceptJoinRequest(Long requestId) {
        Optional<StudyRoomParticipantEntity> optionalRequest = studyRoomParticipantRepository.findById(requestId);
        if (optionalRequest.isEmpty()) {
            throw new IllegalArgumentException("Join request not found with ID: " + requestId);
        }

        StudyRoomParticipantEntity participant = optionalRequest.get();
        if (participant.getStatus() != StudyRoomParticipantEntity.ParticipantStatus.PENDING) {
            throw new IllegalArgumentException("Request is not in a PENDING state.");
        }

        participant.setStatus(StudyRoomParticipantEntity.ParticipantStatus.ACCEPTED);
        return studyRoomParticipantRepository.save(participant);
    }

    public StudyRoomParticipantEntity joinStudyRoom(Long studyRoomId, Integer userId, String roomKey, boolean acceptTerms) {
        // Fetch the study room entity
        Optional<StudyRoomEntity> studyRoomOptional = studyRoomRepository.findById(studyRoomId);
        if (studyRoomOptional.isEmpty()) {
            throw new IllegalArgumentException("Study room not found with ID: " + studyRoomId);
        }

        StudyRoomEntity studyRoom = studyRoomOptional.get();

        // Check if the roomKey matches
        if (!studyRoom.getRoomKey().equals(roomKey)) {
            throw new IllegalArgumentException("Invalid room key provided.");
        }

        // Check if the user accepts terms and conditions
        if (!acceptTerms) {
            throw new IllegalArgumentException("You must accept the terms and conditions.");
        }

        // Check if the user is already a participant
        boolean alreadyParticipant = studyRoom.getParticipants().stream()
                .anyMatch(participant -> participant.getUserId().equals(userId));
        if (alreadyParticipant) {
            throw new IllegalArgumentException("User is already a participant in the study room.");
        }

        // Add the user to the participants
        StudyRoomParticipantEntity participant = new StudyRoomParticipantEntity();
        participant.setStudyRoom(studyRoom);
        participant.setUserId(userId);
        participant.setStatus(StudyRoomParticipantEntity.ParticipantStatus.ACCEPTED);

        return studyRoomParticipantRepository.save(participant);
    }

}
