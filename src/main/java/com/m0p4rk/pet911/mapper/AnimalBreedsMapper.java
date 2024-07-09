package com.m0p4rk.pet911.mapper;

import com.m0p4rk.pet911.model.AnimalBreeds;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnimalBreedsMapper {

    @Select("SELECT * FROM animal_breeds WHERE id = #{id}")
    AnimalBreeds findById(@Param("id") Long id);

    @Select("SELECT * FROM animal_breeds WHERE name = #{name}")
    AnimalBreeds findByName(@Param("name") String name);

    @Select("SELECT * FROM animal_breeds")
    List<AnimalBreeds> findAll();

    @Select("SELECT * FROM animal_breeds WHERE species_id = #{speciesId}")
    List<AnimalBreeds> findBySpeciesId(@Param("speciesId") Long speciesId);

    @Insert("INSERT INTO animal_breeds (name, species_id) VALUES (#{name}, #{speciesId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AnimalBreeds breeds);

    @Update("UPDATE animal_breeds SET name = #{name}, species_id = #{speciesId} WHERE id = #{id}")
    int update(AnimalBreeds breeds);

    @Delete("DELETE FROM animal_breeds WHERE id = #{id}")
    int delete(@Param("id") Long id);
}