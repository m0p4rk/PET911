package com.m0p4rk.pet911.service.impl;

import com.m0p4rk.pet911.mapper.AnimalBreedsMapper;
import com.m0p4rk.pet911.model.AnimalBreeds;
import com.m0p4rk.pet911.service.AnimalBreedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalBreedsServiceImpl implements AnimalBreedsService {

    private final AnimalBreedsMapper animalBreedsMapper;

    @Autowired
    public AnimalBreedsServiceImpl(AnimalBreedsMapper animalBreedsMapper){
        this.animalBreedsMapper = animalBreedsMapper;
    }

    @Override
    public AnimalBreeds findById(Long id) {
        return animalBreedsMapper.findById(id);
    }

    @Override
    public AnimalBreeds findByName(String name) {
        return animalBreedsMapper.findByName(name);
    }

    @Override
    public List<AnimalBreeds> findAll() {
        return animalBreedsMapper.findAll();
    }

    @Override
    public List<AnimalBreeds> findBySpeciesId(Long speciesId) {
        return animalBreedsMapper.findBySpeciesId(speciesId);
    }

    @Override
    public AnimalBreeds save(AnimalBreeds breeds) {
        animalBreedsMapper.insert(breeds);
        return breeds;
    }

    @Override
    public void update(AnimalBreeds breeds) {
        animalBreedsMapper.update(breeds);
    }

    @Override
    public void delete(Long id) {
        animalBreedsMapper.delete(id);
    }
}