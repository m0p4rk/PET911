package com.m0p4rk.pet911.mapper.util;

import com.m0p4rk.pet911.dto.PetDTO;
import com.m0p4rk.pet911.model.Pet;

public class PetMapperUtil {

    public static PetDTO mapToDTO(Pet pet) {
        if (pet == null) {
            return null;
        }
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setUserId(pet.getUserId());
        petDTO.setName(pet.getName());
        petDTO.setSpecies(pet.getSpecies());
        petDTO.setBreed(pet.getBreed());
        petDTO.setAge(pet.getAge());
        petDTO.setWeight(pet.getWeight());
        return petDTO;
    }

    public static Pet mapToEntity(PetDTO petDTO) {
        if (petDTO == null) {
            return null;
        }
        Pet pet = new Pet();
        pet.setId(petDTO.getId());
        pet.setUserId(petDTO.getUserId());
        pet.setName(petDTO.getName());
        pet.setSpecies(petDTO.getSpecies());
        pet.setBreed(petDTO.getBreed());
        pet.setAge(petDTO.getAge());
        pet.setWeight(petDTO.getWeight());
        return pet;
    }
}
