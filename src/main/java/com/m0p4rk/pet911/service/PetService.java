package com.m0p4rk.pet911.service;

import com.m0p4rk.pet911.dto.PetDTO;
import com.m0p4rk.pet911.model.Pet;

import java.util.List;

public interface PetService {
    List<PetDTO> findByUserId(Long userId);
    PetDTO findById(Long Id);
    void savePet(PetDTO petDTO);
    void updatePet(PetDTO petDTO);
    void deletePet(Long id);
}
