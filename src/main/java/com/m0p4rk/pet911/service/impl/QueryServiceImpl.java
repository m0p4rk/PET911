package com.m0p4rk.pet911.service.impl;

import com.m0p4rk.pet911.service.QueryService;
import com.m0p4rk.pet911.service.EmergencyGuideService;
import com.m0p4rk.pet911.model.QueryRequest;
import com.m0p4rk.pet911.model.QueryResponse;
import com.m0p4rk.pet911.model.EmergencyGuide;
import com.m0p4rk.pet911.enums.QueryCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QueryServiceImpl implements QueryService {

    private final EmergencyGuideService emergencyGuideService;
    private final Map<String, QueryCategory> keywordCategoryMap;
    private final Map<String, String> speciesKeywords;
    private final Map<String, String> symptomKeywords;

    @Autowired
    public QueryServiceImpl(EmergencyGuideService emergencyGuideService) {
        this.emergencyGuideService = emergencyGuideService;
        this.keywordCategoryMap = initializeCategoryKeywords();
        this.speciesKeywords = initializeSpeciesKeywords();
        this.symptomKeywords = initializeSymptomKeywords();
    }

    @Override
    public QueryResponse processQuery(QueryRequest request) {
        QueryCategory category = categorizeQuery(request.getQuestionText());

        return switch (category) {
            case HEALTH_GUIDE -> provideHealthGuide(request);
            default -> provideGeneralResponse(request);
        };
    }

    @Override
    public QueryCategory categorizeQuery(String questionText) {
        String lowercaseText = questionText.toLowerCase();

        // Check for health-related keywords
        for (String keyword : keywordCategoryMap.keySet()) {
            if (lowercaseText.contains(keyword)) {
                return keywordCategoryMap.get(keyword);
            }
        }

        // Check for specific symptoms
        for (String symptom : symptomKeywords.keySet()) {
            if (lowercaseText.contains(symptom)) {
                return QueryCategory.HEALTH_GUIDE;
            }
        }

        return QueryCategory.GENERAL;
    }

    @Override
    public QueryResponse provideHealthGuide(QueryRequest request) {
        String species = extractSpecies(request.getQuestionText());
        List<String> symptoms = extractSymptoms(request.getQuestionText());
        List<EmergencyGuide> guides = emergencyGuideService.findBySpeciesAndSymptoms(species, String.join(",", symptoms));

        QueryResponse response = new QueryResponse();
        response.setCategory(QueryCategory.HEALTH_GUIDE);
        response.setResponseText(buildResponseText(guides, species, symptoms));
        return response;
    }

    @Override
    public QueryResponse provideGeneralResponse(QueryRequest request) {
        QueryResponse response = new QueryResponse();
        response.setCategory(QueryCategory.GENERAL);
        response.setResponseText("I'm sorry, I couldn't find a specific answer to your question. " +
                "For pet health concerns, please provide more details about your pet's species and symptoms. " +
                "If you need immediate assistance, please contact your local veterinarian.");
        return response;
    }

    private String extractSpecies(String questionText) {
        return speciesKeywords.entrySet().stream()
                .filter(entry -> questionText.toLowerCase().contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse("Unknown");
    }

    private List<String> extractSymptoms(String questionText) {
        return symptomKeywords.entrySet().stream()
                .filter(entry -> questionText.toLowerCase().contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private String buildResponseText(List<EmergencyGuide> guides, String species, List<String> symptoms) {
        if (guides.isEmpty()) {
            return String.format("I'm sorry, I couldn't find any specific guide for a %s with %s. " +
                            "This combination of symptoms may require immediate veterinary attention. " +
                            "Please consult with a veterinarian for personalized advice as soon as possible.",
                    species, String.join(" and ", symptoms));
        }

        StringBuilder response = new StringBuilder(String.format("Here are some guidelines for a %s with %s:\n\n",
                species, String.join(" and ", symptoms)));
        guides.forEach(guide ->
                response.append(String.format("Step %d: %s\n", guide.getGuideStep(), guide.getGuideText()))
        );
        response.append("\nPlease note that these are general guidelines. If symptoms persist or worsen, " +
                "or if you're unsure about your pet's condition, always consult with a veterinarian.");
        return response.toString();
    }

    private Map<String, QueryCategory> initializeCategoryKeywords() {
        Map<String, QueryCategory> map = new HashMap<>();
        map.put("health", QueryCategory.HEALTH_GUIDE);
        map.put("sick", QueryCategory.HEALTH_GUIDE);
        map.put("symptom", QueryCategory.HEALTH_GUIDE);
        map.put("ill", QueryCategory.HEALTH_GUIDE);
        map.put("disease", QueryCategory.HEALTH_GUIDE);
        map.put("emergency", QueryCategory.HEALTH_GUIDE);
        map.put("vet", QueryCategory.HEALTH_GUIDE);
        map.put("medicine", QueryCategory.HEALTH_GUIDE);
        map.put("treatment", QueryCategory.HEALTH_GUIDE);
        return map;
    }

    private Map<String, String> initializeSpeciesKeywords() {
        Map<String, String> map = new HashMap<>();
        map.put("dog", "Dog");
        map.put("puppy", "Dog");
        map.put("canine", "Dog");
        map.put("cat", "Cat");
        map.put("kitten", "Cat");
        map.put("feline", "Cat");
        map.put("bird", "Bird");
        map.put("parrot", "Bird");
        map.put("fish", "Fish");
        map.put("rabbit", "Rabbit");
        map.put("hamster", "Hamster");
        map.put("guinea pig", "Guinea Pig");
        return map;
    }

    private Map<String, String> initializeSymptomKeywords() {
        Map<String, String> map = new HashMap<>();

        // Vomiting
        map.put("vomit", "Vomiting");
        map.put("throw up", "Vomiting");
        map.put("nausea", "Vomiting");
        map.put("regurgitate", "Vomiting");

        // Diarrhea
        map.put("diarrhea", "Diarrhea");
        map.put("loose stool", "Diarrhea");
        map.put("watery stool", "Diarrhea");

        // Seizures
        map.put("seizure", "Seizures");
        map.put("fit", "Seizures");
        map.put("convulsion", "Seizures");
        map.put("epilepsy", "Seizures");

        // Bleeding
        map.put("bleed", "Bleeding");
        map.put("blood", "Bleeding");
        map.put("hemorrhage", "Bleeding");
        map.put("wound", "Bleeding");

        // Difficulty Breathing
        map.put("breathing difficulty", "Difficulty Breathing");
        map.put("shortness of breath", "Difficulty Breathing");
        map.put("gasping", "Difficulty Breathing");
        map.put("wheezing", "Difficulty Breathing");
        map.put("coughing", "Difficulty Breathing");

        // Ingestion of Toxins
        map.put("poison", "Ingestion of Toxins");
        map.put("toxic", "Ingestion of Toxins");
        map.put("eat something bad", "Ingestion of Toxins");
        map.put("swallow", "Ingestion of Toxins");
        map.put("ingest", "Ingestion of Toxins");

        // Additional common emergency symptoms
        map.put("not eating", "Loss of Appetite");
        map.put("won't eat", "Loss of Appetite");
        map.put("anorexia", "Loss of Appetite");
        map.put("fever", "Fever");
        map.put("high temperature", "Fever");
        map.put("lethargic", "Lethargy");
        map.put("weak", "Lethargy");
        map.put("tired", "Lethargy");
        map.put("collapse", "Collapse");
        map.put("faint", "Collapse");
        map.put("unconscious", "Collapse");

        // Additional symptoms
        map.put("limp", "Lameness");
        map.put("swelling", "Swelling");
        map.put("itch", "Itching");
        map.put("scratch", "Itching");
        map.put("rash", "Skin Issues");
        map.put("hair loss", "Skin Issues");
        map.put("eye problem", "Eye Issues");
        map.put("ear infection", "Ear Issues");
        map.put("dental", "Dental Issues");
        map.put("tooth", "Dental Issues");

        return map;
    }
}