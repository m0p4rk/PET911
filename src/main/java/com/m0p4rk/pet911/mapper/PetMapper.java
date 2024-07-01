package com.m0p4rk.pet911.mapper;

import com.m0p4rk.pet911.model.Pet;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PetMapper {
    @Select("SELECT * FROM Pet WHERE user_id = #{userId}")
    List<Pet> findByUserId(Long userId);

    @Select("SELECT * FROM Pet WHERE id = #{id}")
    Pet findById(Long id);

    @Insert("INSERT INTO Pet (user_id, name, species, breed, age, weight) VALUES (#{userId}, #{name}, #{species}, #{breed}, #{age}, #{weight})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Pet pet);

    @Update("UPDATE Pet SET name = #{name}, species = #{species}, breed = #{breed}, age = #{age}, weight = #{weight} WHERE id = #{id}")
    void update(Pet pet);

    @Delete("DELETE FROM Pet WHERE id = #{id}")
    void delete(Long id);
}
