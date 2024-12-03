package com.example.Study_Hive_Backend.invitation.dto;

import lombok.Data;

@Data
public class InvitationRequest {
    private Integer receiverId;
    private Long roomId;
    private String roomKey;
}