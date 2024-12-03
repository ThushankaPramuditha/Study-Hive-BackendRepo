package com.example.Study_Hive_Backend.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "community")
public class CommunityEntity {
    @Id
    @Column(name = "community_id", unique = true, updatable = false)
    private Long community_id;
    private String community_name;
    private String community_description;
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private ComUserEntity community_admin;
    @OneToMany(mappedBy = "community")
    private List<CommunityPostEntity> posts;


}
