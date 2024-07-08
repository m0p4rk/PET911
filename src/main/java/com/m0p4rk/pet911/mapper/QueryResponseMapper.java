package com.m0p4rk.pet911.mapper;

import com.m0p4rk.pet911.model.QueryResponse;
import com.m0p4rk.pet911.typehandler.QueryCategoryTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QueryResponseMapper {

    @Insert("INSERT INTO queryresponse (query_request_id, user_id, emergency_log_id, response_date, category, response_text, created_at) " +
            "VALUES (#{queryRequestId}, #{userId}, #{emergencyLogId}, #{responseDate}, #{category}, #{responseText}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(QueryResponse queryResponse);

    @Select("SELECT * FROM queryresponse WHERE id = #{id}")
    @Results(id = "queryResponseResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "queryRequestId", column = "query_request_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "emergencyLogId", column = "emergency_log_id"),
            @Result(property = "responseDate", column = "response_date"),
            @Result(property = "category", column = "category", typeHandler = QueryCategoryTypeHandler.class),
            @Result(property = "responseText", column = "response_text"),
            @Result(property = "createdAt", column = "created_at")
    })
    QueryResponse findById(Long id);

    @Select("SELECT * FROM queryresponse WHERE query_request_id = #{queryRequestId}")
    @ResultMap("queryResponseResultMap")
    QueryResponse findByQueryRequestId(Long queryRequestId);

    @Select("SELECT * FROM queryresponse")
    @ResultMap("queryResponseResultMap")
    List<QueryResponse> findAll();

    // 추가적인 쿼리 메서드들...
}