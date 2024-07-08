package com.m0p4rk.pet911.service;

import com.m0p4rk.pet911.model.EmergencyGuide;

import java.util.List;
import java.util.Optional;

public interface EmergencyGuideService {

    EmergencyGuide findById(Long id);

    List<EmergencyGuide> findBySpeciesAndSymptoms(String species, String symptoms);

    List<EmergencyGuide> findAll();

    int insert(EmergencyGuide emergencyGuide);

    int update(EmergencyGuide emergencyGuide);

    int delete(Long id);

    Optional<EmergencyGuide> findBySpeciesAndSymptomsAndGuideStep(String species, String symptoms, int guideStep);

    void insertOrUpdate(EmergencyGuide emergencyGuide);

    long getTotalGuides();

    int getSpeciesCoverage();

    String getMostCommonSymptom();
}