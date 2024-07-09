package com.m0p4rk.pet911.service.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.m0p4rk.pet911.model.EmergencyGuide;
import com.m0p4rk.pet911.model.AnimalSpecies;
import com.m0p4rk.pet911.service.EmergencyGuideService;
import com.m0p4rk.pet911.service.AnimalSpeciesService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class DataLoaderService implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoaderService.class);

    @Autowired
    private EmergencyGuideService emergencyGuideService;

    @Autowired
    private AnimalSpeciesService animalSpeciesService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        loadJsonFile("JSON/cat_emergency_guide.json");
        loadJsonFile("JSON/dog_emergency_guide.json");
    }

    private void loadJsonFile(String resourcePath) {
        try {
            InputStream inputStream = new ClassPathResource(resourcePath).getInputStream();
            List<EmergencyGuideDTO> guidesList = objectMapper.readValue(inputStream, new TypeReference<>() {
            });

            for (EmergencyGuideDTO guideDTO : guidesList) {
                AnimalSpecies species = getOrCreateSpecies(guideDTO.getSpecies());

                for (GuideStepDTO guideStep : guideDTO.getGuide()) {
                    EmergencyGuide emergencyGuide = new EmergencyGuide();
                    emergencyGuide.setSpeciesId(species.getId());
                    emergencyGuide.setSymptoms(guideDTO.getSymptoms());
                    emergencyGuide.setGuideStep(guideStep.getGuideStep());
                    emergencyGuide.setGuideText(guideStep.getGuideText());
                    emergencyGuideService.insertOrUpdate(emergencyGuide);
                }
            }
        } catch (IOException e) {
            logger.error("Error loading JSON file: " + resourcePath, e);
        }
    }

    private AnimalSpecies getOrCreateSpecies(String speciesName) {
        AnimalSpecies species = animalSpeciesService.findByName(speciesName);
        if (species == null) {
            species = new AnimalSpecies();
            species.setName(speciesName);
            species = animalSpeciesService.save(species);
        }
        return species;
    }

    // DTO classes
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class EmergencyGuideDTO {
        private String species;
        private String symptoms;
        private List<GuideStepDTO> guide;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class GuideStepDTO {
        @JsonProperty("guide_step")
        private int guideStep;

        @JsonProperty("guide_text")
        private String guideText;
    }
}