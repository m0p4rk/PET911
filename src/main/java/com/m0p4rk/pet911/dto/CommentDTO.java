package com.m0p4rk.pet911.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
public class CommentDTO {
    Long id;
    Long communityId;
    Long user_id;
    String content;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
