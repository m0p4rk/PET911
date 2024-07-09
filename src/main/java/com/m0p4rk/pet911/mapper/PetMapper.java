package com.m0p4rk.pet911.mapper;

import com.m0p4rk.pet911.model.Pet;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PetMapper {
    @Select("SELECT * FROM pet WHERE user_id = #{userId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "speciesId", column = "species_id"),
            @Result(property = "breedId", column = "breed_id"),
            @Result(property = "age", column = "age"),
            @Result(property = "weight", column = "weight"),
            @Result(property = "createdAt", column = "created_at")
    })
    List<Pet> findByUserId(Long userId);

    @Select("SELECT * FROM pet WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "speciesId", column = "species_id"),
            @Result(property = "breedId", column = "breed_id"),
            @Result(property = "age", column = "age"),
            @Result(property = "weight", column = "weight"),
            @Result(property = "createdAt", column = "created_at")
    })
    Pet findById(Long id);

    @Insert("INSERT INTO pet (user_id, name, species_id, breed_id, age, weight) " +
            "VALUES (#{userId}, #{name}, #{speciesId}, #{breedId}, #{age}, #{weight})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Pet pet);

    @Update("UPDATE pet SET name = #{name}, species_id = #{speciesId}, breed_id = #{breedId}, " +
            "age = #{age}, weight = #{weight} WHERE id = #{id}")
    void update(Pet pet);

    @Delete("DELETE FROM pet WHERE id = #{id}")
    void delete(Long id);

    @Select("SELECT * FROM pet WHERE species_id = #{speciesId}")
    List<Pet> findBySpeciesId(Long speciesId);

    @Select("SELECT * FROM pet WHERE breed_id = #{breedId}")
    List<Pet> findByBreedId(Long breedId);
}