package com.example.Study_Hive_Backend.newcommunity.subgroup;

import com.example.Study_Hive_Backend.newcommunity.community.Community;
import com.example.Study_Hive_Backend.newcommunity.community.CommunityRepository;
import com.example.Study_Hive_Backend.user.User;
import com.example.Study_Hive_Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubgroupService {

    @Autowired
    private SubgroupRepository subgroupRepository;

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private UserRepository userRepository;

    public Subgroup createSubgroup(Long communityId, Subgroup subgroup) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        subgroup.setCommunity(community);
        return subgroupRepository.save(subgroup);
    }

    public Subgroup getSubgroup(Long id) {
        return subgroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subgroup not found"));
    }

    public List<Subgroup> getSubgroupsByCommunity(Long communityId) {
        return subgroupRepository.findByCommunityId(communityId);
    }

    public Subgroup updateSubgroup(Long id, Subgroup subgroupDetails) {
        Subgroup subgroup = getSubgroup(id);
        subgroup.setName(subgroupDetails.getName());
        subgroup.setDescription(subgroupDetails.getDescription());
        return subgroupRepository.save(subgroup);
    }

    public void deleteSubgroup(Long id) {
        subgroupRepository.deleteById(id);
    }

    public Subgroup addMember(Long subgroupId, Integer userId) {
        Subgroup subgroup = getSubgroup(subgroupId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        subgroup.getMembers().add(user);
        return subgroupRepository.save(subgroup);
    }

    public Subgroup removeMember(Long subgroupId, Integer userId) {
        Subgroup subgroup = getSubgroup(subgroupId);
        subgroup.getMembers().removeIf(member -> member.getId().equals(userId));
        return subgroupRepository.save(subgroup);
    }
}

