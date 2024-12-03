package com.example.Study_Hive_Backend.newcommunity.post;

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
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private UserRepository userRepository;

    public PostDTO createPost(Long communityId, Integer userId, Post post) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        post.setCommunity(community);
        post.setAuthor(user);
        post = postRepository.save(post);

        return mapToDTO(post);
    }

    public PostDTO getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return mapToDTO(post);
    }

    public List<PostDTO> getPostsByCommunity(Long communityId) {
        List<Post> posts = postRepository.findByCommunityId(communityId);
        return posts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public PostDTO updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setContent(postDetails.getContent());
        post.setImageUrl(postDetails.getImageUrl());
        post = postRepository.save(post);

        return mapToDTO(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

//    private PostDTO mapToDTO(Post post) {
//        PostDTO dto = new PostDTO();
//        dto.setId(post.getId());
//        dto.setContent(post.getContent());
//        dto.setImageUrl(post.getImageUrl());
//        dto.setAuthorId(Long.valueOf(post.getAuthor().getId()));
//        dto.setAuthorUsername(post.getAuthor().getUsername());
//        dto.setCommunityId(post.getCommunity().getId());
//        dto.setCommunityName(post.getCommunity().getName());
//        dto.setCreatedAt(post.getCreatedAt());
//        return dto;
//    }

    private PostDTO mapToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setImageUrl(post.getImageUrl());
        dto.setAuthorId(Long.valueOf(post.getAuthor().getId()));
        dto.setAuthorUsername(post.getAuthor().getUsername());

        // Assuming you have a way to get the member's first and last name from the author
        // For this, we need to access the user or member data related to the author
        MemberDTO memberDTO = getMemberDTO(Long.valueOf(post.getAuthor().getId())); // Implement this method as needed

        if (memberDTO != null) {
            dto.setAuthorFirstName(memberDTO.getFirstname());
            dto.setAuthorLastName(memberDTO.getLastname());
        }

        dto.setCommunityId(post.getCommunity().getId());
        dto.setCommunityName(post.getCommunity().getName());
        dto.setCreatedAt(post.getCreatedAt());
        return dto;
    }

    // Example method to retrieve the MemberDTO based on author ID (implement as needed)
    private MemberDTO getMemberDTO(Long authorId) {
        User author = userRepository.findById(Math.toIntExact(authorId)).orElse(null);
        if (author != null) {
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setId(author.getId());
            memberDTO.setFirstname(author.getFirstname());
            memberDTO.setLastname(author.getLastname());
            memberDTO.setEmail(author.getEmail()); // If needed
            return memberDTO;
        }
        return null;
    }

}
