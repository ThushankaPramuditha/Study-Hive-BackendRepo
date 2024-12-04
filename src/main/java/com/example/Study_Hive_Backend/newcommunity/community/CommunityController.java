package com.example.Study_Hive_Backend.newcommunity.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communities")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @PostMapping
    public ResponseEntity<CommunityDTO> createCommunity(@RequestBody Community community) {
        return ResponseEntity.ok(communityService.createCommunity(community));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommunityDTO> getCommunity(@PathVariable Long id) {
        return ResponseEntity.ok(communityService.getCommunity(id));
    }

    @GetMapping
    public ResponseEntity<List<CommunityDTO>> getAllCommunities() {
        return ResponseEntity.ok(communityService.getAllCommunities());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommunityDTO> updateCommunity(@PathVariable Long id, @RequestBody Community community) {
        return ResponseEntity.ok(communityService.updateCommunity(id, community));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable Long id) {
        communityService.deleteCommunity(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{communityId}/members/{userId}")
    public ResponseEntity<CommunityDTO> addMember(@PathVariable Long communityId, @PathVariable Integer userId) {
        return ResponseEntity.ok(communityService.addMember(communityId, userId));
    }

    @DeleteMapping("/{communityId}/members/{userId}")
    public ResponseEntity<CommunityDTO> removeMember(@PathVariable Long communityId, @PathVariable Integer userId) {
        return ResponseEntity.ok(communityService.removeMember(communityId, userId));
    }
}
