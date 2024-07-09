package com.m0p4rk.pet911.controller;

import com.m0p4rk.pet911.dto.CommentDTO;
import com.m0p4rk.pet911.service.CommunityCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community/comments")
public class CommunityCommentController {

    private final CommunityCommentService commentService;

    @Autowired
    public CommunityCommentController(CommunityCommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long id) {
        CommentDTO comment = commentService.getComment(id);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/community/{communityId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByCommunityId(@PathVariable Long communityId) {
        List<CommentDTO> comments = commentService.getCommentsByCommunityId(communityId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO createdComment = commentService.createComment(commentDTO);
        return ResponseEntity.ok(createdComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        CommentDTO updatedComment = commentService.updateComment(id, commentDTO);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count/{communityId}")
    public ResponseEntity<Integer> getCommentCount(@PathVariable Long communityId) {
        int count = commentService.getCommentCount(communityId);
        return ResponseEntity.ok(count);
    }
}