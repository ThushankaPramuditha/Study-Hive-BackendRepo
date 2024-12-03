package com.example.Study_Hive_Backend.newcommunity.announcements;

import com.example.Study_Hive_Backend.newcommunity.community.Community;
import com.example.Study_Hive_Backend.newcommunity.community.CommunityRepository;
import com.example.Study_Hive_Backend.newcommunity.community.MemberDTO;
import com.example.Study_Hive_Backend.user.User;
import com.example.Study_Hive_Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private UserRepository userRepository;

    public AnnouncementDTO createAnnouncement(Long communityId, Integer userId, AnnouncementDTO announcementDTO) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Announcement announcement = new Announcement();
        announcement.setTitle(announcementDTO.getTitle());
        announcement.setContent(announcementDTO.getContent());
        announcement.setCommunity(community);
        announcement.setAuthor(user);

        Announcement savedAnnouncement = announcementRepository.save(announcement);
        return convertToDTO(savedAnnouncement);
    }

    public AnnouncementDTO getAnnouncement(Long id) {
        return announcementRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));
    }

    public List<AnnouncementDTO> getAnnouncementsByCommunity(Long communityId) {
        List<Announcement> announcements = announcementRepository.findByCommunityId(communityId);
        return announcements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AnnouncementDTO updateAnnouncement(Long id, AnnouncementDTO announcementDTO) {
        Announcement announcement = getAnnouncementEntity(id);
        announcement.setTitle(announcementDTO.getTitle());
        announcement.setContent(announcementDTO.getContent());
        Announcement updatedAnnouncement = announcementRepository.save(announcement);
        return convertToDTO(updatedAnnouncement);
    }

    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }

    private Announcement getAnnouncementEntity(Long id) {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));
    }

    private AnnouncementDTO convertToDTO(Announcement announcement) {
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setId(announcement.getId());
        announcementDTO.setTitle(announcement.getTitle());
        announcementDTO.setContent(announcement.getContent());
        announcementDTO.setCreatedAt(announcement.getCreatedAt()); // Map createdAt

        if (announcement.getCommunity() != null) {
            announcementDTO.setCommunityId(announcement.getCommunity().getId());
            announcementDTO.setCommunityName(announcement.getCommunity().getName());
        }

        // Map user details for the author if necessary
        if (announcement.getAuthor() != null) {
            User author = announcement.getAuthor();
            announcementDTO.setAuthorId(author.getId());
            announcementDTO.setAuthorName(author.getFirstname() + " " + author.getLastname());
        }

        return announcementDTO;
    }

}
