package com.example.Study_Hive_Backend.community.controller;


import com.example.Study_Hive_Backend.community.entity.CommunityPostEntity;
import com.example.Study_Hive_Backend.community.service.CommunityPostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/community/post")
public class CommunityPostController {

    private final CommunityPostService communityPostService;

    public CommunityPostController(CommunityPostService communityPostService) {
        this.communityPostService = communityPostService;
    }


    @PostMapping("/save")
    public CommunityPostEntity savePost(@RequestBody CommunityPostEntity communityPostEntity) {
        return communityPostService.savePost(communityPostEntity);
    }

    @GetMapping("/find/{id}")
    public CommunityPostEntity getPost( @PathVariable Integer id) {
        return communityPostService.getPost(id);
    }

    @GetMapping("/findAll")
    public List<CommunityPostEntity> getAllPost() {

       return communityPostService.getAllPost();
    }
    @PutMapping("/update")
    public CommunityPostEntity updatePost(@RequestBody CommunityPostEntity communityPostEntity) {
        return communityPostService.updatePost(communityPostEntity);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePost(@PathVariable Integer id) {
       return communityPostService.deletePost(id);
    }


}
