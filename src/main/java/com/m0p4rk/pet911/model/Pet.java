package com.m0p4rk.pet911.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class Pet {
    private Long id;
    private Long userId;
    private String name;
    private Long speciesId;
    private Long breedId;
    private Integer age;
    private Double weight;
    private Timestamp createdAt;
}