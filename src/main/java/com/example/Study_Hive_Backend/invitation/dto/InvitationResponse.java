package com.example.Study_Hive_Backend.invitation.dto;

import lombok.Data;

@Data
public class InvitationResponse {
    private Long invitationId;
    private String roomId;
    private String roomKey;

    public InvitationResponse(Long invitationId, String roomId, String roomKey) {
        this.invitationId = invitationId;
        this.roomId = roomId;
        this.roomKey = roomKey;
    }
}