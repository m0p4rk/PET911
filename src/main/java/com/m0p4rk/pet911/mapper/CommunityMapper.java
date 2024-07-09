package com.m0p4rk.pet911.mapper;

import com.m0p4rk.pet911.model.Community;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CommunityMapper {

    @Insert("INSERT INTO community (user_id, title, content, category, status, view_count, is_pinned, created_at, updated_at) " +
            "VALUES (#{userId}, #{title}, #{content}, #{category}, #{status}, #{viewCount}, #{isPinned}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Community community);

    @Select("SELECT * FROM community WHERE id = #{id}")
    Community selectById(Long id);

    @Select("SELECT * FROM community ORDER BY created_at DESC LIMIT #{offset}, #{limit}")
    List<Community> selectAll(@Param("offset") int offset, @Param("limit") int limit);

    @Select("SELECT * FROM community WHERE category = #{category} ORDER BY created_at DESC LIMIT #{offset}, #{limit}")
    List<Community> selectByCategory(@Param("category") String category, @Param("offset") int offset, @Param("limit") int limit);

    @Update("UPDATE community SET title = #{title}, content = #{content}, category = #{category}, " +
            "status = #{status}, updated_at = NOW() WHERE id = #{id}")
    void update(Community community);

    @Delete("DELETE FROM community WHERE id = #{id}")
    void delete(Long id);

    @Update("UPDATE community SET view_count = view_count + 1 WHERE id = #{id}")
    void incrementViewCount(Long id);

    @Update("UPDATE community SET is_pinned = NOT is_pinned WHERE id = #{id}")
    void togglePin(Long id);

    @Select("SELECT COUNT(*) FROM community")
    int countAll();

    @Select("SELECT COUNT(*) FROM community WHERE category = #{category}")
    int countByCategory(String category);
}