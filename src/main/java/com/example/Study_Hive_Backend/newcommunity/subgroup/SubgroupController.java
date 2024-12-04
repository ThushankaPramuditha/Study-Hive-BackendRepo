package com.example.Study_Hive_Backend.newcommunity.subgroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communities/{communityId}/subgroups")
public class SubgroupController {

    @Autowired
    private SubgroupService subgroupService;

    @PostMapping
    public ResponseEntity<Subgroup> createSubgroup(@PathVariable Long communityId, @RequestBody Subgroup subgroup) {
        return ResponseEntity.ok(subgroupService.createSubgroup(communityId, subgroup));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subgroup> getSubgroup(@PathVariable Long id) {
        return ResponseEntity.ok(subgroupService.getSubgroup(id));
    }

    @GetMapping
    public ResponseEntity<List<Subgroup>> getSubgroupsByCommunity(@PathVariable Long communityId) {
        return ResponseEntity.ok(subgroupService.getSubgroupsByCommunity(communityId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subgroup> updateSubgroup(@PathVariable Long id, @RequestBody Subgroup subgroup) {
        return ResponseEntity.ok(subgroupService.updateSubgroup(id, subgroup));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubgroup(@PathVariable Long id) {
        subgroupService.deleteSubgroup(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{subgroupId}/members/{userId}")
    public ResponseEntity<Subgroup> addMember(@PathVariable Long subgroupId, @PathVariable Integer userId) {
        return ResponseEntity.ok(subgroupService.addMember(subgroupId, userId));
    }

    @DeleteMapping("/{subgroupId}/members/{userId}")
    public ResponseEntity<Subgroup> removeMember(@PathVariable Long subgroupId, @PathVariable Integer userId) {
        return ResponseEntity.ok(subgroupService.removeMember(subgroupId, userId));
    }
}

