package com.m0p4rk.pet911.service;

import com.m0p4rk.pet911.dto.PetDTO;

import java.util.List;

public interface PetService {
    List<PetDTO> findByUserId(Long userId);
    PetDTO findById(Long id);
    void savePet(PetDTO petDTO);
    void updatePet(PetDTO petDTO);
    void deletePet(Long id);
    List<PetDTO> findBySpeciesId(Long speciesId);
    List<PetDTO> findByBreedId(Long breedId);
}