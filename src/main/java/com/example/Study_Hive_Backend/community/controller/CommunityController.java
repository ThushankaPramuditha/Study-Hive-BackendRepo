package com.example.Study_Hive_Backend.community.controller;

import com.example.Study_Hive_Backend.community.dto.CommunityDTO;
import com.example.Study_Hive_Backend.community.entity.Community;
import com.example.Study_Hive_Backend.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communities")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    // Get all communities or search by name
    @GetMapping
    public List<Community> getCommunities(@RequestParam(value = "search", required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return communityService.searchCommunities(search);
        }
        return communityService.getAllCommunities();
    }

    // Create a new community
    @PostMapping
    public ResponseEntity<Community> createCommunity(@RequestBody CommunityDTO communityDTO) {
        Community community = communityService.createCommunity(communityDTO);
        return new ResponseEntity<>(community, HttpStatus.CREATED);
    }

    // Update an existing community
    @PutMapping("/{id}")
    public ResponseEntity<Community> updateCommunity(@PathVariable Long id, @RequestBody CommunityDTO communityDTO) {
        Community updatedCommunity = communityService.updateCommunity(id, communityDTO);
        if (updatedCommunity != null) {
            return new ResponseEntity<>(updatedCommunity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete a community
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable Long id) {
        boolean isDeleted = communityService.deleteCommunity(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
