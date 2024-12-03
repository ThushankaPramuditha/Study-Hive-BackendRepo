package com.example.Study_Hive_Backend.community.service;


import com.example.Study_Hive_Backend.community.entity.CommunityPostEntity;

import java.util.List;

public interface CommunityPostService {
    CommunityPostEntity savePost(CommunityPostEntity communityPostEntity);

    CommunityPostEntity getPost(Integer id);

    String deletePost(Integer id);

    CommunityPostEntity updatePost(CommunityPostEntity communityPostEntity);

    void deleteAllPost();

    List<CommunityPostEntity> getAllPost();

}
