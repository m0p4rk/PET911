package com.m0p4rk.pet911.service.impl;

import com.m0p4rk.pet911.dto.PetDTO;
import com.m0p4rk.pet911.mapper.PetMapper;
import com.m0p4rk.pet911.model.Pet;
import com.m0p4rk.pet911.service.PetService;
import com.m0p4rk.pet911.mapper.util.PetMapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    private final PetMapper petMapper;

    public PetServiceImpl(PetMapper petMapper) {
        this.petMapper = petMapper;
    }

    @Override
    public List<PetDTO> findByUserId(Long userId) {
        List<Pet> pets = petMapper.findByUserId(userId);
        return pets.stream().map(PetMapperUtil::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public PetDTO findById(Long id) {
        Pet pet = petMapper.findById(id);
        return PetMapperUtil.mapToDTO(pet);
    }

    @Override
    public void savePet(PetDTO petDTO) {
        Pet pet = PetMapperUtil.mapToEntity(petDTO);
        petMapper.insert(pet);
    }

    @Override
    public void updatePet(PetDTO petDTO) {
        Pet pet = PetMapperUtil.mapToEntity(petDTO);
        petMapper.update(pet);
    }

    @Override
    public void deletePet(Long id) {
        petMapper.delete(id);
    }
}
