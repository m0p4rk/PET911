package com.m0p4rk.pet911.service;

import com.m0p4rk.pet911.model.Community;
import java.util.List;

public interface CommunityService {
    Community createPost(Community community);
    Community getPost(Long id);
    List<Community> getAllPosts(int pageNum, int pageSize);
    List<Community> getPostsByCategory(String category, int pageNum, int pageSize);
    Community updatePost(Long id, Community community);
    void deletePost(Long id);
    void incrementViewCount(Long id);
    void togglePin(Long id);
    int getTotalPostCount();
    int getPostCountByCategory(String category);
}