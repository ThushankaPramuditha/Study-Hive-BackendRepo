package com.example.Study_Hive_Backend.invitation.controller;

import com.example.Study_Hive_Backend.invitation.dto.InvitationRequest;
import com.example.Study_Hive_Backend.invitation.dto.InvitationResponse;
import com.example.Study_Hive_Backend.invitation.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    @Autowired
    private InvitationService invitationService;

    @PostMapping("/send")
    public ResponseEntity<InvitationResponse> sendInvitation(@RequestBody InvitationRequest request, Authentication authentication) {
        String senderEmail = authentication.getName();
        InvitationResponse response = invitationService.sendInvitation(senderEmail, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{invitationId}/accept")
    public ResponseEntity<InvitationResponse> acceptInvitation(@PathVariable Long invitationId, Authentication authentication) {
        String receiverEmail = authentication.getName();
        InvitationResponse response = invitationService.acceptInvitation(invitationId, receiverEmail);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{invitationId}/decline")
    public ResponseEntity<Void> declineInvitation(@PathVariable Long invitationId, Authentication authentication) {
        String receiverEmail = authentication.getName();
        invitationService.declineInvitation(invitationId, receiverEmail);
        return ResponseEntity.ok().build();
    }
}

