package com.example.Study_Hive_Backend.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private Integer userId;  // Include userId
    private String userFname;
    private String userLname;
    private boolean profileExists;
}
