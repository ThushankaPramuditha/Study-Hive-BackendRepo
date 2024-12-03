package com.example.Study_Hive_Backend.community.service;

//package com.example.Study_Hive_Backend.community.service;
//
//
//
//
//import com.example.Study_Hive_Backend.community.dto.CommunityDTO;
//import com.example.Study_Hive_Backend.community.entity.Community;
//import com.example.Study_Hive_Backend.community.repository.CommunityRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CommunityService {
//
//    @Autowired
//    private CommunityRepository communityRepository;
//
//    // Get all communities
//    public List<Community> getAllCommunities() {
//        return communityRepository.findAll();
//    }
//
//    // Search communities by name
//    public List<Community> searchCommunities(String searchTerm) {
//        return communityRepository.findByNameContainingIgnoreCase(searchTerm);
//    }
//
//    // Create a new community
//    public Community createCommunity(CommunityDTO communityDTO) {
//        Community community = new Community();
//        community.setName(communityDTO.getName());
//        community.setDescription(communityDTO.getDescription());
//        community.setCreated(communityDTO.getCreated());
//        return communityRepository.save(community);
//    }
//
//    // Update an existing community
//    public Community updateCommunity(Long id, CommunityDTO communityDTO) {
//        Optional<Community> communityOpt = communityRepository.findById(id);
//        if (communityOpt.isPresent()) {
//            Community community = communityOpt.get();
//            community.setName(communityDTO.getName());
//            community.setDescription(communityDTO.getDescription());
//            community.setCreated(communityDTO.getCreated());
//            return communityRepository.save(community);
//        }
//        return null;
//    }
//
//    // Delete a community
//    public boolean deleteCommunity(Long id) {
//        Optional<Community> communityOpt = communityRepository.findById(id);
//        if (communityOpt.isPresent()) {
//            communityRepository.delete(communityOpt.get());
//            return true;
//        }
//        return false;
//    }
//}
//



import com.example.Study_Hive_Backend.community.dto.CommunityDTO;
import com.example.Study_Hive_Backend.community.entity.Community;
import com.example.Study_Hive_Backend.community.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityService {

    @Autowired
    private CommunityRepository communityRepository;

    // Get all communities
    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    // Search communities by name
    public List<Community> searchCommunities(String searchTerm) {
        return communityRepository.findByNameContainingIgnoreCase(searchTerm);
    }

    // Create a new community
    public Community createCommunity(CommunityDTO communityDTO) {
        Community community = new Community();
        community.setName(communityDTO.getName());
        community.setDescription(communityDTO.getDescription());
        community.setCreated(communityDTO.getCreated());
        community.setMemberCount(communityDTO.getMemberCount());
        return communityRepository.save(community);
    }

    // Update an existing community
    public Community updateCommunity(Long id, CommunityDTO communityDTO) {
        Optional<Community> communityOpt = communityRepository.findById(id);
        if (communityOpt.isPresent()) {
            Community community = communityOpt.get();
            community.setName(communityDTO.getName());
            community.setDescription(communityDTO.getDescription());
            community.setCreated(communityDTO.getCreated());
            community.setMemberCount(communityDTO.getMemberCount());
            return communityRepository.save(community);
        }
        return null;
    }

    // Delete a community
    public boolean deleteCommunity(Long id) {
        Optional<Community> communityOpt = communityRepository.findById(id);
        if (communityOpt.isPresent()) {
            communityRepository.delete(communityOpt.get());
            return true;
        }
        return false;
    }
}
