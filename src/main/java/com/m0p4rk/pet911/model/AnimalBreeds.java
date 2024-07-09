package com.m0p4rk.pet911.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnimalBreeds {
    private Long id;
    private String name;
    private Long speciesId;
}