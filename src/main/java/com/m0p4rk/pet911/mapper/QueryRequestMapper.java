package com.m0p4rk.pet911.mapper;

import com.m0p4rk.pet911.model.QueryRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QueryRequestMapper {

    @Insert("INSERT INTO queryrequest (user_id, request_date, category, species, question_text, created_at) " +
            "VALUES (#{userId}, #{requestDate}, #{category}, #{species}, #{questionText}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(QueryRequest queryRequest);

    @Select("SELECT * FROM queryrequest WHERE id = #{id}")
    @Results(id = "queryRequestResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "requestDate", column = "request_date"),
            @Result(property = "category", column = "category"),
            @Result(property = "species", column = "species"),
            @Result(property = "questionText", column = "question_text"),
            @Result(property = "createdAt", column = "created_at")
    })
    QueryRequest findById(Long id);

    @Select("SELECT * FROM queryrequest")
    @ResultMap("queryRequestResultMap")
    List<QueryRequest> findAll();

    // 추가적인 쿼리 메서드들...
}