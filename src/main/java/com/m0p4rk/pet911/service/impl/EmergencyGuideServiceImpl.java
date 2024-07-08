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
    public List<EmergencyGuide> findBySpeciesAndSymptoms(String species, String symptoms) {
        return emergencyGuideMapper.findBySpeciesAndSymptoms(species, symptoms);
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
    public Optional<EmergencyGuide> findBySpeciesAndSymptomsAndGuideStep(String species, String symptoms, int guideStep) {
        return emergencyGuideMapper.findBySpeciesAndSymptomsAndGuideStep(species, symptoms, guideStep);
    }

    @Override
    public void insertOrUpdate(EmergencyGuide emergencyGuide) {
        Optional<EmergencyGuide> existingGuide = findBySpeciesAndSymptomsAndGuideStep(
                emergencyGuide.getSpecies(),
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