package com.m0p4rk.pet911.service.impl;

import com.m0p4rk.pet911.dto.UserSessionDTO;
import com.m0p4rk.pet911.model.Community;
import com.m0p4rk.pet911.mapper.CommunityMapper;
import com.m0p4rk.pet911.service.CommunityService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    private final CommunityMapper communityMapper;

    @Autowired
    public CommunityServiceImpl(CommunityMapper communityMapper) {
        this.communityMapper = communityMapper;
    }

    @Override
    @Transactional
    public Community createPost(Community community) {
        UserSessionDTO userSession = getCurrentUser();
        if (userSession == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        community.setUserId(userSession.getId());
        community.setStatus("PUBLISHED");
        community.setCreatedAt(LocalDateTime.now());
        community.setUpdatedAt(LocalDateTime.now());
        communityMapper.insert(community);
        return community;
    }

    private UserSessionDTO getCurrentUser() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        if (session != null) {
            return (UserSessionDTO) session.getAttribute("user");
        }
        return null;
    }
    // 세션 처리를 중앙화 시킬 필요가 있음

    @Override
    @Transactional(readOnly = true)
    public Community getPost(Long id) {
        Community community = communityMapper.selectById(id);
        if (community == null) {
            throw new RuntimeException("Post not found");
        }
        return community;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Community> getAllPosts(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return communityMapper.selectAll(offset, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Community> getPostsByCategory(String category, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return communityMapper.selectByCategory(category, offset, pageSize);
    }

    @Override
    @Transactional
    public Community updatePost(Long id, Community community) {
        Community existingPost = communityMapper.selectById(id);
        if (existingPost == null) {
            throw new RuntimeException("Post not found");
        }

        community.setId(id);
        communityMapper.update(community);

        return community;
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        communityMapper.delete(id);
    }

    @Override
    @Transactional
    public void incrementViewCount(Long id) {
        communityMapper.incrementViewCount(id);
    }

    @Override
    @Transactional
    public void togglePin(Long id) {
        communityMapper.togglePin(id);
    }

    @Override
    @Transactional(readOnly = true)
    public int getTotalPostCount() {
        return communityMapper.countAll();
    }

    @Override
    @Transactional(readOnly = true)
    public int getPostCountByCategory(String category) {
        return communityMapper.countByCategory(category);
    }
}