package com.m0p4rk.pet911.mapper.util;

import com.m0p4rk.pet911.dto.PetDTO;
import com.m0p4rk.pet911.model.Pet;
import com.m0p4rk.pet911.service.AnimalBreedsService;
import com.m0p4rk.pet911.service.AnimalSpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PetMapperUtil {

    private final AnimalBreedsService animalBreedsService;
    private final AnimalSpeciesService animalSpeciesService;

    @Autowired
    public PetMapperUtil(AnimalBreedsService animalBreedsService, AnimalSpeciesService animalSpeciesService) {
        this.animalBreedsService = animalBreedsService;
        this.animalSpeciesService = animalSpeciesService;
    }

    public PetDTO mapToDTO(Pet pet) {
        if (pet == null) {
            System.out.println("Pet is null");
            return null;
        }
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setUserId(pet.getUserId());
        petDTO.setName(pet.getName());

        System.out.println("Mapping Pet to PetDTO");
        System.out.println("Pet: " + pet);

        if (pet.getSpeciesId() != null) {
            var species = animalSpeciesService.findById(pet.getSpeciesId());
            String speciesName = species != null ? species.getName() : null;
            petDTO.setSpecies(speciesName);
            System.out.println("Species ID: " + pet.getSpeciesId() + " -> Species Name: " + speciesName);
        } else {
            System.out.println("Species ID is null");
        }

        if (pet.getBreedId() != null) {
            var breed = animalBreedsService.findById(pet.getBreedId());
            String breedName = breed != null ? breed.getName() : null;
            petDTO.setBreed(breedName);
            System.out.println("Breed ID: " + pet.getBreedId() + " -> Breed Name: " + breedName);
        } else {
            System.out.println("Breed ID is null");
        }

        petDTO.setAge(pet.getAge());
        petDTO.setWeight(pet.getWeight());
        System.out.println("Mapped PetDTO: " + petDTO);
        return petDTO;
    }


    public Pet mapToEntity(PetDTO petDTO) {
        if (petDTO == null) {
            System.out.println("PetDTO is null");
            return null;
        }

        Pet pet = new Pet();
        System.out.println("Mapping PetDTO to Pet entity...");

        pet.setId(petDTO.getId());
        System.out.println("Pet ID: " + petDTO.getId());

        pet.setUserId(petDTO.getUserId());
        System.out.println("User ID: " + petDTO.getUserId());

        pet.setName(petDTO.getName());
        System.out.println("Pet Name: " + petDTO.getName());

        if (petDTO.getSpecies() != null) {
            var species = animalSpeciesService.findByName(petDTO.getSpecies());
            Long speciesId = species != null ? species.getId() : null;
            pet.setSpeciesId(speciesId);
            System.out.println("Species Name: " + petDTO.getSpecies() + " -> Species ID: " + speciesId);
        } else {
            System.out.println("Species is null");
        }

        if (petDTO.getBreed() != null) {
            var breed = animalBreedsService.findByName(petDTO.getBreed());
            Long breedId = breed != null ? breed.getId() : null;
            pet.setBreedId(breedId);
            System.out.println("Breed Name: " + petDTO.getBreed() + " -> Breed ID: " + breedId);
        } else {
            System.out.println("Breed is null");
        }

        pet.setAge(petDTO.getAge());
        System.out.println("Pet Age: " + petDTO.getAge());

        pet.setWeight(petDTO.getWeight());
        System.out.println("Pet Weight: " + petDTO.getWeight());

        System.out.println("Mapping complete. Pet entity: " + pet);
        return pet;
    }


    public List<PetDTO> mapToDTOList(List<Pet> pets) {
        return pets.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<Pet> mapToEntityList(List<PetDTO> petDTOs) {
        return petDTOs.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDTO(Pet pet, PetDTO petDTO) {
        pet.setName(petDTO.getName());

        if (petDTO.getSpecies() != null) {
            var species = animalSpeciesService.findByName(petDTO.getSpecies());
            pet.setSpeciesId(species != null ? species.getId() : null);
        }

        if (petDTO.getBreed() != null) {
            var breed = animalBreedsService.findByName(petDTO.getBreed());
            pet.setBreedId(breed != null ? breed.getId() : null);
        }

        pet.setAge(petDTO.getAge());
        pet.setWeight(petDTO.getWeight());
    }

    public void updateDTOFromEntity(PetDTO petDTO, Pet pet) {
        petDTO.setId(pet.getId());
        petDTO.setUserId(pet.getUserId());
        petDTO.setName(pet.getName());

        if (pet.getSpeciesId() != null) {
            var species = animalSpeciesService.findById(pet.getSpeciesId());
            petDTO.setSpecies(species != null ? species.getName() : null);
        }

        if (pet.getBreedId() != null) {
            var breed = animalBreedsService.findById(pet.getBreedId());
            petDTO.setBreed(breed != null ? breed.getName() : null);
        }

        petDTO.setAge(pet.getAge());
        petDTO.setWeight(pet.getWeight());
    }
}
