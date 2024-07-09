package com.m0p4rk.pet911.controller;

import com.m0p4rk.pet911.model.EmergencyGuide;
import com.m0p4rk.pet911.service.EmergencyGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/emergency-guide")
public class EmergencyGuideController {

    private final EmergencyGuideService emergencyGuideService;

    @Autowired
    public EmergencyGuideController(EmergencyGuideService emergencyGuideService) {
        this.emergencyGuideService = emergencyGuideService;
    }

    @GetMapping("/dashboard")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        Map<String, Object> dashboardData = new HashMap<>();
        dashboardData.put("totalGuides", emergencyGuideService.getTotalGuides());
        dashboardData.put("speciesCoverage", emergencyGuideService.getSpeciesCoverage());
        dashboardData.put("commonSymptom", emergencyGuideService.getMostCommonSymptom());
        return ResponseEntity.ok(dashboardData);
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<EmergencyGuide>> searchEmergencyGuide(
            @RequestParam String species,
            @RequestParam String symptom) {
        List<EmergencyGuide> guide = emergencyGuideService.findBySpeciesNameAndSymptoms(species,symptom);
        System.out.println("guide callback result :" + guide);
        return ResponseEntity.ok(guide);
    }
}