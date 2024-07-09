package com.m0p4rk.pet911.service;

import com.m0p4rk.pet911.dto.CommentDTO;
import java.util.List;

public interface CommunityCommentService {
    CommentDTO getComment(Long id);
    List<CommentDTO> getCommentsByCommunityId(Long communityId);
    CommentDTO createComment(CommentDTO commentDTO);
    CommentDTO updateComment(Long id, CommentDTO commentDTO);
    void deleteComment(Long id);
    int getCommentCount(Long communityId);
}