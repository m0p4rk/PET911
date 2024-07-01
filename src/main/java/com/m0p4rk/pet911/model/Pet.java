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
    private String species;
    private String breed;
    private int age;
    private double weight;
    private Timestamp createdAt;
}
