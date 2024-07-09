package com.m0p4rk.pet911.service.impl;

import com.m0p4rk.pet911.dto.PetDTO;
import com.m0p4rk.pet911.mapper.PetMapper;
import com.m0p4rk.pet911.model.Pet;
import com.m0p4rk.pet911.service.PetService;
import com.m0p4rk.pet911.mapper.util.PetMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    private final PetMapper petMapper;
    private final PetMapperUtil petMapperUtil;

    @Autowired
    public PetServiceImpl(PetMapper petMapper, PetMapperUtil petMapperUtil) {
        this.petMapper = petMapper;
        this.petMapperUtil = petMapperUtil;
    }

    @Override
    public List<PetDTO> findByUserId(Long userId) {
        List<Pet> pets = petMapper.findByUserId(userId);
        pets.forEach(pet -> System.out.println("Pet found by User ID: " + pet));
        List<PetDTO> petDTOs = pets.stream().map(petMapperUtil::mapToDTO).collect(Collectors.toList());
        petDTOs.forEach(petDTO -> System.out.println("PetDTO: " + petDTO));
        return petDTOs;
    }

    @Override
    public PetDTO findById(Long id) {
        Pet pet = petMapper.findById(id);
        System.out.println("Pet found by ID: " + pet);
        PetDTO petDTO = petMapperUtil.mapToDTO(pet);
        System.out.println("Found PetDTO by ID: " + petDTO);
        return petDTO;
    }

    @Override
    public void savePet(PetDTO petDTO) {
        Pet pet = petMapperUtil.mapToEntity(petDTO);
        petMapper.insert(pet);
    }

    @Override
    public void updatePet(PetDTO petDTO) {
        Pet pet = petMapperUtil.mapToEntity(petDTO);
        petMapper.update(pet);
    }

    @Override
    public void deletePet(Long id) {
        petMapper.delete(id);
    }

    @Override
    public List<PetDTO> findBySpeciesId(Long speciesId) {
        List<Pet> pets = petMapper.findBySpeciesId(speciesId);
        return pets.stream().map(petMapperUtil::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<PetDTO> findByBreedId(Long breedId) {
        List<Pet> pets = petMapper.findByBreedId(breedId);
        return pets.stream().map(petMapperUtil::mapToDTO).collect(Collectors.toList());
    }
}
