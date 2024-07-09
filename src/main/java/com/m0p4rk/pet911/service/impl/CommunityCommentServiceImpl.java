package com.m0p4rk.pet911.service.impl;

import com.m0p4rk.pet911.dto.CommentDTO;
import com.m0p4rk.pet911.dto.UserSessionDTO;
import com.m0p4rk.pet911.mapper.CommunityCommentMapper;
import com.m0p4rk.pet911.service.CommunityCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommunityCommentServiceImpl implements CommunityCommentService {

    private final CommunityCommentMapper commentMapper;

    @Autowired
    public CommunityCommentServiceImpl(CommunityCommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentDTO getComment(Long id) {
        return commentMapper.selectById(id);
    }

    @Override
    public List<CommentDTO> getCommentsByCommunityId(Long communityId) {
        return commentMapper.selectByCommunityId(communityId);
    }

    @Override
    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO) {
        UserSessionDTO userSession = getCurrentUser();
        if (userSession == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        commentDTO.setUser_id(userSession.getId());
        commentDTO.setCreatedAt(LocalDateTime.now());
        commentDTO.setUpdatedAt(LocalDateTime.now());
        commentMapper.insert(commentDTO);
        return commentDTO;
    }

    @Override
    @Transactional
    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        CommentDTO existingComment = commentMapper.selectById(id);
        if (existingComment == null) {
            throw new RuntimeException("댓글을 찾을 수 없습니다.");
        }
        UserSessionDTO userSession = getCurrentUser();
        if (userSession == null || !userSession.getId().equals(existingComment.getUser_id())) {
            throw new RuntimeException("댓글을 수정할 권한이 없습니다.");
        }
        commentDTO.setId(id);
        commentDTO.setUpdatedAt(LocalDateTime.now());
        commentMapper.update(commentDTO);
        return commentDTO;
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        CommentDTO existingComment = commentMapper.selectById(id);
        if (existingComment == null) {
            throw new RuntimeException("댓글을 찾을 수 없습니다.");
        }
        UserSessionDTO userSession = getCurrentUser();
        if (userSession == null || !userSession.getId().equals(existingComment.getUser_id())) {
            throw new RuntimeException("댓글을 삭제할 권한이 없습니다.");
        }
        commentMapper.delete(id);
    }

    @Override
    public int getCommentCount(Long communityId) {
        return commentMapper.countByCommunityId(communityId);
    }

    private UserSessionDTO getCurrentUser() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        if (session != null) {
            return (UserSessionDTO) session.getAttribute("user");
        }
        return null;
    }
}