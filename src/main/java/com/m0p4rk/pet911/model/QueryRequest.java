package com.m0p4rk.pet911.model;

import com.m0p4rk.pet911.enums.QueryCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryRequest {
    private Long id;
    private Long userId;
    private LocalDateTime requestDate;
    private QueryCategory category;
    private String species;
    private String questionText;
    private LocalDateTime createdAt;
}