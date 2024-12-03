package com.example.Study_Hive_Backend.community.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "community")
public class CommunityEntity {
    @Id
    @Column(name = "community_id", unique = true, updatable = false)
    private Long community_id;
    private String community_post_id;
    private String community_name;
    private String community_description;

}
