package com.m0p4rk.pet911.mapper;

import com.m0p4rk.pet911.model.AnimalSpecies;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnimalSpeciesMapper {

    @Select("SELECT * FROM animal_species WHERE id = #{id}")
    AnimalSpecies findById(@Param("id") Long id);

    @Select("SELECT * FROM animal_species WHERE name = #{name}")
    AnimalSpecies findByName(@Param("name") String name);

    @Select("SELECT * FROM animal_species")
    List<AnimalSpecies> findAll();

    @Insert("INSERT INTO animal_species (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AnimalSpecies species);

    @Update("UPDATE animal_species SET name = #{name} WHERE id = #{id}")
    int update(AnimalSpecies species);

    @Delete("DELETE FROM animal_species WHERE id = #{id}")
    int delete(@Param("id") Long id);
}