package com.m0p4rk.pet911.service;

import com.m0p4rk.pet911.model.AnimalSpecies;

import java.util.List;

public interface AnimalSpeciesService {
    AnimalSpecies findById(Long id);
    AnimalSpecies findByName(String name);
    List<AnimalSpecies> findAll();
    AnimalSpecies save(AnimalSpecies species);
    void update(AnimalSpecies species);
    void delete(Long id);
}