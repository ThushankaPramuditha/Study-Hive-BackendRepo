package com.example.Study_Hive_Backend.newcommunity.announcements;

import com.example.Study_Hive_Backend.newcommunity.community.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communities/{communityId}/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<AnnouncementDTO> createAnnouncement(
            @PathVariable Long communityId,
            @RequestParam Integer userId,
            @RequestBody AnnouncementDTO announcementDTO) {

        return ResponseEntity.ok(announcementService.createAnnouncement(communityId, userId, announcementDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> getAnnouncement(@PathVariable Long id) {
        return ResponseEntity.ok(announcementService.getAnnouncement(id));
    }

    @GetMapping
    public ResponseEntity<List<AnnouncementDTO>> getAnnouncementsByCommunity(@PathVariable Long communityId) {
        return ResponseEntity.ok(announcementService.getAnnouncementsByCommunity(communityId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> updateAnnouncement(
            @PathVariable Long id,
            @RequestBody AnnouncementDTO announcementDTO) {

        return ResponseEntity.ok(announcementService.updateAnnouncement(id, announcementDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.ok().build();
    }
}
