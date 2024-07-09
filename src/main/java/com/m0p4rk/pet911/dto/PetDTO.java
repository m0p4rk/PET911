package com.m0p4rk.pet911.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class PetDTO {
    private Long id;
    private Long userId;
    private String name;
    private String species;
    private String breed;
    private Integer age;
    private Double weight;
    private Timestamp createdAt;
}