package com.example.Study_Hive_Backend.studyroom.service;

import com.example.Study_Hive_Backend.config.JwtService;
import com.example.Study_Hive_Backend.studyroom.entity.StudyRoomEntity;
import com.example.Study_Hive_Backend.studyroom.entity.StudyRoomParticipantEntity;
import com.example.Study_Hive_Backend.studyroom.entity.UserRoomTime;
import com.example.Study_Hive_Backend.studyroom.repository.StudyRoomParticipantRepository;
import com.example.Study_Hive_Backend.studyroom.repository.StudyRoomRepository;
import com.example.Study_Hive_Backend.studyroom.repository.UserRoomTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;
    private final StudyRoomParticipantRepository studyRoomParticipantRepository;
    private final UserRoomTimeRepository userRoomTimeRepository;
    private final JwtService jwtService;

    @Autowired
    public StudyRoomService(StudyRoomRepository studyRoomRepository,
                            StudyRoomParticipantRepository studyRoomParticipantRepository,
                            UserRoomTimeRepository userRoomTimeRepository, JwtService jwtService) {
        this.studyRoomRepository = studyRoomRepository;
        this.studyRoomParticipantRepository = studyRoomParticipantRepository;
        this.userRoomTimeRepository = userRoomTimeRepository;
        this.jwtService = jwtService;
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

    public List<StudyRoomEntity> getStudyRoomsByUserId(Integer userId) {
        List<StudyRoomEntity> ownedRooms = studyRoomRepository.findByOwnerId(userId);
        List<StudyRoomEntity> participantRooms = studyRoomParticipantRepository.findByUserIdAndStatus(userId, StudyRoomParticipantEntity.ParticipantStatus.ACCEPTED)
                .stream()
                .map(StudyRoomParticipantEntity::getStudyRoom)
                .toList();

        ownedRooms.addAll(participantRooms);
        return ownedRooms.stream().distinct().collect(Collectors.toList());
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
        Optional<StudyRoomEntity> studyRoomOptional = studyRoomRepository.findById(studyRoomId);
        if (studyRoomOptional.isEmpty()) {
            throw new IllegalArgumentException("Study room not found with ID: " + studyRoomId);
        }

        StudyRoomEntity studyRoom = studyRoomOptional.get();

        if (!studyRoom.getRoomKey().equals(roomKey)) {
            throw new IllegalArgumentException("Invalid room key provided.");
        }

        if (!acceptTerms) {
            throw new IllegalArgumentException("You must accept the terms and conditions.");
        }

        StudyRoomParticipantEntity participant = new StudyRoomParticipantEntity();
        participant.setStudyRoom(studyRoom);
        participant.setUserId(userId);
        participant.setStatus(StudyRoomParticipantEntity.ParticipantStatus.ACCEPTED);

        return studyRoomParticipantRepository.save(participant);
    }

//    public List<StudyRoomParticipantEntity> getAcceptedUsers(Long roomId) {
//        return studyRoomParticipantRepository.findByStudyRoomIdAndStatus(roomId, StudyRoomParticipantEntity.ParticipantStatus.ACCEPTED);
//    }

    public List<Map<String, Object>> getAcceptedUsers(Long roomId, String token) {
        List<StudyRoomParticipantEntity> acceptedUsers = studyRoomParticipantRepository.findByStudyRoomIdAndStatus(roomId, StudyRoomParticipantEntity.ParticipantStatus.ACCEPTED);

        return acceptedUsers.stream().map(user -> {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userId", user.getUserId());
            userInfo.put("firstName", jwtService.extractClaim(token, claims -> claims.get("firstname", String.class)));
            userInfo.put("lastName", jwtService.extractClaim(token, claims -> claims.get("lastname", String.class)));
            return userInfo;
        }).collect(Collectors.toList());
    }

    public void leaveRoom(Long roomId, Integer userId, Long timeSpent) {
        List<StudyRoomParticipantEntity> participants = studyRoomParticipantRepository
                .findAllByStudyRoomIdAndUserId(roomId, userId);

        if (participants.isEmpty()) {
            throw new IllegalArgumentException("User is not a participant in this room");
        }

        // Handle potential duplicate entries
        for (StudyRoomParticipantEntity participant : participants) {
            participant.setStatus(StudyRoomParticipantEntity.ParticipantStatus.PENDING);
            studyRoomParticipantRepository.save(participant);
        }

        // Only save one UserRoomTime entry
        UserRoomTime userRoomTime = new UserRoomTime();
        userRoomTime.setUserId(userId);
        userRoomTime.setRoomId(roomId);
        userRoomTime.setTimeSpent(timeSpent);
        userRoomTimeRepository.save(userRoomTime);
    }
}

