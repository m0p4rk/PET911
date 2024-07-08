package com.m0p4rk.pet911.model;

import com.m0p4rk.pet911.enums.QueryCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryResponse {
    private Long id;
    private Long queryRequestId;
    private Long userId;
    private Long emergencyLogId;
    private LocalDateTime responseDate;
    private QueryCategory category;
    private String responseText;
    private LocalDateTime createdAt;
}