package com.m0p4rk.pet911.service.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.m0p4rk.pet911.model.EmergencyGuide;
import com.m0p4rk.pet911.service.EmergencyGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class DataLoaderService {

    @Autowired
    private EmergencyGuideService emergencyGuideService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void loadJsonData() {
        loadJsonFile("src/main/resources/JSON/cat_emergency_guide.json");
        loadJsonFile("src/main/resources/JSON/dog_emergency_guide.json");
    }

    private void loadJsonFile(String filePath) {
        try {
            List<Map<String, Object>> guidesList = objectMapper.readValue(new File(filePath), new TypeReference<List<Map<String, Object>>>() {});
            for (Map<String, Object> guideMap : guidesList) {
                String species = (String) guideMap.get("species");
                String symptoms = (String) guideMap.get("symptoms");
                List<Map<String, Object>> guides = (List<Map<String, Object>>) guideMap.get("guide");

                for (Map<String, Object> guide : guides) {
                    EmergencyGuide emergencyGuide = new EmergencyGuide();
                    emergencyGuide.setSpecies(species);
                    emergencyGuide.setSymptoms(symptoms);
                    emergencyGuide.setGuideStep((int) guide.get("guide_step"));
                    emergencyGuide.setGuideText((String) guide.get("guide_text"));
                    emergencyGuideService.insertOrUpdate(emergencyGuide);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}