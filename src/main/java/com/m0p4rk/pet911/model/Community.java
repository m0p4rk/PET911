package com.m0p4rk.pet911.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Community {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String category;
    private String status = "PUBLISHED"; // 기본값 설정
    private int viewCount = 0;
    private boolean isPinned = false;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 생성자, getter, setter 등은 그대로 유지
}