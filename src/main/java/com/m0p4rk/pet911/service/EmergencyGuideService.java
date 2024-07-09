package com.m0p4rk.pet911.service;

import com.m0p4rk.pet911.model.EmergencyGuide;

import java.util.List;
import java.util.Optional;

public interface EmergencyGuideService {

    EmergencyGuide findById(Long id);

    List<EmergencyGuide> findAll();

    int insert(EmergencyGuide emergencyGuide);

    int update(EmergencyGuide emergencyGuide);

    int delete(Long id);

    List<EmergencyGuide> findBySpeciesNameAndSymptoms(String name, String symptoms);

    List<EmergencyGuide> findBySpeciesIdAndSymptoms(Long speciesId, String symptoms);

    Optional<EmergencyGuide> findBySpeciesIdAndSymptomsAndGuideStep(Long speciesId, String symptoms, int guideStep);

    void insertOrUpdate(EmergencyGuide emergencyGuide);

    long getTotalGuides();

    int getSpeciesCoverage();

    String getMostCommonSymptom();
}