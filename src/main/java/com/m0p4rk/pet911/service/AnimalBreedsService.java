package com.m0p4rk.pet911.service;

import com.m0p4rk.pet911.model.AnimalBreeds;

import java.util.List;

public interface AnimalBreedsService {

    AnimalBreeds findById(Long id);
    AnimalBreeds findByName(String name);
    List<AnimalBreeds> findAll();
    List<AnimalBreeds> findBySpeciesId(Long speciesId);
    AnimalBreeds save(AnimalBreeds breeds);
    void update(AnimalBreeds breeds);
    void delete(Long id);
}