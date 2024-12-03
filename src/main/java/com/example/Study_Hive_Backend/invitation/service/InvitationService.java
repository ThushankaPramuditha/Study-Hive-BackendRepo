package com.example.Study_Hive_Backend.invitation.service;

import com.example.Study_Hive_Backend.invitation.dto.InvitationRequest;
import com.example.Study_Hive_Backend.invitation.dto.InvitationResponse;
import com.example.Study_Hive_Backend.invitation.entity.Invitation;
import com.example.Study_Hive_Backend.invitation.repository.InvitationRepository;
import com.example.Study_Hive_Backend.user.User;
import com.example.Study_Hive_Backend.studyroom.service.StudyRoomService;
import com.example.Study_Hive_Backend.user.UserRepository;
import com.example.Study_Hive_Backend.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class InvitationService {

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRoomService studyRoomService;

    @Autowired
    private WebSocketServer webSocketServer;

    @Transactional
    public InvitationResponse sendInvitation(String senderEmail, InvitationRequest request) {
        User senderUser = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new RuntimeException("Sender user not found"));
        User receiverUser = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver user not found"));

        Invitation invitation = new Invitation();
        invitation.setSender(senderUser.getProfile());
        invitation.setReceiver(receiverUser.getProfile());
        invitation.setRoomId(request.getRoomId().toString());
        invitation.setRoomKey(request.getRoomKey());
        invitation.setStatus("PENDING");
        invitation.setCreatedAt(LocalDateTime.now());

        invitation = invitationRepository.save(invitation);

        sendNotification(receiverUser.getId().toString(), "You have received a study room invitation");
        sendInvitationUpdate(receiverUser.getId().toString(), "SENT", invitation.getId());

        return new InvitationResponse(invitation.getId(), invitation.getRoomId(), invitation.getRoomKey());
    }

    @Transactional
    public InvitationResponse acceptInvitation(Long invitationId, String receiverEmail) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("Invitation not found"));
        User receiverUser = userRepository.findByEmail(receiverEmail)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (!invitation.getReceiver().getUser().getId().equals(receiverUser.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        invitation.setStatus("ACCEPTED");
        invitationRepository.save(invitation);

        studyRoomService.joinStudyRoom(
                Long.valueOf(invitation.getRoomId()),
                receiverUser.getId(),
                invitation.getRoomKey(),
                true
        );

        sendNotification(
                invitation.getSender().getUser().getId().toString(),
                "Your study room invitation has been accepted"
        );
        sendInvitationUpdate(invitation.getSender().getUser().getId().toString(), "ACCEPTED", invitation.getId());

        return new InvitationResponse(invitation.getId(), invitation.getRoomId(), invitation.getRoomKey());
    }

    @Transactional
    public void declineInvitation(Long invitationId, String receiverEmail) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("Invitation not found"));
        User receiverUser = userRepository.findByEmail(receiverEmail)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (!invitation.getReceiver().getUser().getId().equals(receiverUser.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        invitation.setStatus("DECLINED");
        invitationRepository.save(invitation);

        sendNotification(
                invitation.getSender().getUser().getId().toString(),
                "Your study room invitation has been declined"
        );
        sendInvitationUpdate(invitation.getSender().getUser().getId().toString(), "DECLINED", invitation.getId());
    }

    private void sendNotification(String userId, String message) {
        webSocketServer.sendNotification(userId, message);
    }

    private void sendInvitationUpdate(String userId, String status, Long invitationId) {
        String message = status + ":" + invitationId;
        webSocketServer.sendNotification(userId, message);
    }
}

