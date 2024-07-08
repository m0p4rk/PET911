package com.m0p4rk.pet911.mapper;

import com.m0p4rk.pet911.model.EmergencyGuide;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EmergencyGuideMapper {

    @Select("SELECT * FROM emergencyguide WHERE id = #{id}")
    @Results(id = "EmergencyGuideResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "species", column = "species"),
            @Result(property = "symptoms", column = "symptoms"),
            @Result(property = "guideStep", column = "guide_step"),
            @Result(property = "guideText", column = "guide_text"),
            @Result(property = "createdAt", column = "created_at")
    })
    EmergencyGuide findById(@Param("id") Long id);

    @Select("SELECT * FROM emergencyguide WHERE species = #{species} AND symptoms = #{symptoms}")
    @ResultMap("EmergencyGuideResultMap")
    List<EmergencyGuide> findBySpeciesAndSymptoms(@Param("species") String species, @Param("symptoms") String symptoms);

    @Select("SELECT * FROM emergencyguide")
    @ResultMap("EmergencyGuideResultMap")
    List<EmergencyGuide> findAll();

    @Insert("INSERT INTO emergencyguide(species, symptoms, guide_step, guide_text) VALUES(#{species}, #{symptoms}, #{guideStep}, #{guideText})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(EmergencyGuide emergencyGuide);

    @Update("UPDATE emergencyguide SET species = #{species}, symptoms = #{symptoms}, guide_step = #{guideStep}, guide_text = #{guideText} WHERE id = #{id}")
    int update(EmergencyGuide emergencyGuide);

    @Delete("DELETE FROM emergencyguide WHERE id = #{id}")
    int delete(@Param("id") Long id);

    @Select("SELECT * FROM emergencyguide WHERE species = #{species} AND symptoms = #{symptoms} AND guide_step = #{guideStep} LIMIT 1")
    @ResultMap("EmergencyGuideResultMap")
    Optional<EmergencyGuide> findBySpeciesAndSymptomsAndGuideStep(@Param("species") String species, @Param("symptoms") String symptoms, @Param("guideStep") int guideStep);

    @Select("SELECT COUNT(*) FROM emergencyguide")
    long count();

    @Select("SELECT COUNT(DISTINCT species) FROM emergencyguide")
    int countDistinctSpecies();

    @Select("SELECT symptoms FROM emergencyguide GROUP BY symptoms ORDER BY COUNT(*) DESC LIMIT 1")
    String findMostCommonSymptom();
}