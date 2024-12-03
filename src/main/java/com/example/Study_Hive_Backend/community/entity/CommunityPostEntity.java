package com.example.Study_Hive_Backend.community.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "community_Post")
@NoArgsConstructor
@AllArgsConstructor
public class CommunityPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "com_post_id", updatable = false, unique = true, nullable = false)
    private Integer id;
    private String post_title;
    private String post_content;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private ComUserEntity user;
    @ManyToOne
    @JoinColumn(name = "community_id")
    private CommunityEntity community; // Getters and setters


}
