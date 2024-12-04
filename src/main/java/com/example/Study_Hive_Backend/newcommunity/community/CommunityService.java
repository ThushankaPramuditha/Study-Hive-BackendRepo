package com.example.Study_Hive_Backend.newcommunity.community;

import com.example.Study_Hive_Backend.user.User;
import com.example.Study_Hive_Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityService {

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private UserRepository userRepository;

    public CommunityDTO createCommunity(Community community) {
        Community savedCommunity = communityRepository.save(community);
        return convertToDTO(savedCommunity);
    }

    public CommunityDTO getCommunity(Long id) {
        return communityRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Community not found"));
    }

    public List<CommunityDTO> getAllCommunities() {
        return communityRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CommunityDTO updateCommunity(Long id, Community communityDetails) {
        Community community = getCommunityEntity(id);
        community.setName(communityDetails.getName());
        community.setDescription(communityDetails.getDescription());
        return convertToDTO(communityRepository.save(community));
    }

    public void deleteCommunity(Long id) {
        communityRepository.deleteById(id);
    }

    public CommunityDTO addMember(Long communityId, Integer userId) {
        Community community = getCommunityEntity(communityId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        community.getMembers().add(user);
        return convertToDTO(communityRepository.save(community));
    }

    public CommunityDTO removeMember(Long communityId, Integer userId) {
        Community community = getCommunityEntity(communityId);
        community.getMembers().removeIf(member -> member.getId().equals(userId));
        return convertToDTO(communityRepository.save(community));
    }

    private Community getCommunityEntity(Long id) {
        return communityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Community not found"));
    }

    private CommunityDTO convertToDTO(Community community) {
        CommunityDTO communityDTO = new CommunityDTO();
        communityDTO.setId(community.getId());
        communityDTO.setName(community.getName());
        communityDTO.setDescription(community.getDescription());
        communityDTO.setMembers(community.getMembers().stream()
                .map(user -> {
                    MemberDTO memberDTO = new MemberDTO();
                    memberDTO.setId(user.getId());
                    memberDTO.setFirstname(user.getFirstname());
                    memberDTO.setLastname(user.getLastname());
                    memberDTO.setEmail(user.getEmail());
                    return memberDTO;
                }).collect(Collectors.toList()));
        return communityDTO;
    }
}
