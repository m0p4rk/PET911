package com.m0p4rk.pet911.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PetDTO {
    private Long id;
    private Long userId;
    private String name;
    private String species;
    private String breed;
    private int age;
    private double weight;
}
