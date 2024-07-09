package com.m0p4rk.pet911.service.impl;

import com.m0p4rk.pet911.mapper.AnimalSpeciesMapper;
import com.m0p4rk.pet911.model.AnimalSpecies;
import com.m0p4rk.pet911.service.AnimalSpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalSpeciesServiceImpl implements AnimalSpeciesService {

    private final AnimalSpeciesMapper animalSpeciesMapper;

    @Autowired
    public AnimalSpeciesServiceImpl(AnimalSpeciesMapper animalSpeciesMapper) {
        this.animalSpeciesMapper = animalSpeciesMapper;
    }

    @Override
    public AnimalSpecies findById(Long id) {
        return animalSpeciesMapper.findById(id);
    }

    @Override
    public AnimalSpecies findByName(String name) {
        return animalSpeciesMapper.findByName(name);
    }

    @Override
    public List<AnimalSpecies> findAll() {
        return animalSpeciesMapper.findAll();
    }

    @Override
    public AnimalSpecies save(AnimalSpecies species) {
        animalSpeciesMapper.insert(species);
        return species;
    }

    @Override
    public void update(AnimalSpecies species) {
        animalSpeciesMapper.update(species);
    }

    @Override
    public void delete(Long id) {
        animalSpeciesMapper.delete(id);
    }
}