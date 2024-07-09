package com.m0p4rk.pet911.service.impl;

import com.m0p4rk.pet911.mapper.EmergencyGuideMapper;
import com.m0p4rk.pet911.model.EmergencyGuide;
import com.m0p4rk.pet911.service.EmergencyGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmergencyGuideServiceImpl implements EmergencyGuideService {

    private final EmergencyGuideMapper emergencyGuideMapper;

    @Autowired
    public EmergencyGuideServiceImpl(EmergencyGuideMapper emergencyGuideMapper) {
        this.emergencyGuideMapper = emergencyGuideMapper;
    }

    @Override
    public EmergencyGuide findById(Long id) {
        return emergencyGuideMapper.findById(id);
    }

    @Override
    public List<EmergencyGuide> findAll() {
        return emergencyGuideMapper.findAll();
    }

    @Override
    public int insert(EmergencyGuide emergencyGuide) {
        return emergencyGuideMapper.insert(emergencyGuide);
    }

    @Override
    public int update(EmergencyGuide emergencyGuide) {
        return emergencyGuideMapper.update(emergencyGuide);
    }

    @Override
    public int delete(Long id) {
        return emergencyGuideMapper.delete(id);
    }

    @Override
    public List<EmergencyGuide> findBySpeciesNameAndSymptoms(String name, String symptoms) {
        return emergencyGuideMapper.findBySpeciesNameAndSymptoms(name, symptoms);
    }

    @Override
    public List<EmergencyGuide> findBySpeciesIdAndSymptoms(Long speciesId, String symptoms) {
        return emergencyGuideMapper.findBySpeciesIdAndSymptoms(speciesId, symptoms);
    }

    @Override
    public Optional<EmergencyGuide> findBySpeciesIdAndSymptomsAndGuideStep(Long speciesId, String symptoms, int guideStep) {
        return emergencyGuideMapper.findBySpeciesIdAndSymptomsAndGuideStep(speciesId, symptoms, guideStep);
    }

    @Override
    public void insertOrUpdate(EmergencyGuide emergencyGuide) {
        Optional<EmergencyGuide> existingGuide = findBySpeciesIdAndSymptomsAndGuideStep(
                emergencyGuide.getSpeciesId(),
                emergencyGuide.getSymptoms(),
                emergencyGuide.getGuideStep()
        );

        if (existingGuide.isPresent()) {
            EmergencyGuide guide = existingGuide.get();
            guide.setGuideText(emergencyGuide.getGuideText());
            update(guide);
        } else {
            insert(emergencyGuide);
        }
    }

    @Override
    public long getTotalGuides() {
        return emergencyGuideMapper.count();
    }

    @Override
    public int getSpeciesCoverage() {
        return emergencyGuideMapper.countDistinctSpecies();
    }

    @Override
    public String getMostCommonSymptom() {
        return emergencyGuideMapper.findMostCommonSymptom();
    }
}