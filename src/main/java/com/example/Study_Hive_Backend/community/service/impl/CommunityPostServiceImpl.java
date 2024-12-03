package com.example.Study_Hive_Backend.community.service.impl;



import com.example.Study_Hive_Backend.community.entity.CommunityPostEntity;
import com.example.Study_Hive_Backend.community.repository.CommunityPostRepo;
import com.example.Study_Hive_Backend.community.service.CommunityPostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityPostServiceImpl implements CommunityPostService {
    private final CommunityPostRepo communityPostRepo;

    public CommunityPostServiceImpl(CommunityPostRepo communityPostRepo) {
        this.communityPostRepo = communityPostRepo;
    }

    @Override
    public CommunityPostEntity savePost(CommunityPostEntity communityPostEntity) {
        return communityPostRepo.save(communityPostEntity);
    }

    @Override
    public CommunityPostEntity getPost(Integer id) {

        return communityPostRepo.findById(id).orElseThrow();
    }

    @Override
    public String deletePost(Integer id) {
        communityPostRepo.deleteById(id);
        return "Post with " + id + " deleted successfully";

    }

    @Override
    public CommunityPostEntity updatePost(CommunityPostEntity communityPostEntity) {
        return communityPostRepo.save(communityPostEntity);
    }

    @Override
    public void deleteAllPost() {
        communityPostRepo.deleteAll();

    }

    @Override
    public List<CommunityPostEntity> getAllPost() {

        return communityPostRepo.findAll();
    }
}
