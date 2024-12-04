package com.example.Study_Hive_Backend.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "com_user")
public class ComUserEntity {
    @Id
    private String user_id;
    private String user_pw;
    private String user_name;
    private String user_email;
    private String user_phone;
    private String user_birth;
}
