package com.example.Study_Hive_Backend.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "community_user")
public class ComUserEntity {
    @Id
    private String user_id;
    private String user_name;
}
