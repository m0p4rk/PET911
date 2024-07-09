package com.m0p4rk.pet911.mapper;

import com.m0p4rk.pet911.dto.CommentDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommunityCommentMapper {
    @Select("SELECT * FROM community_comment WHERE id = #{id}")
    CommentDTO selectById(Long id);

    @Select("SELECT * FROM community_comment WHERE community_id = #{communityId} ORDER BY created_at DESC")
    List<CommentDTO> selectByCommunityId(Long communityId);

    @Insert("INSERT INTO community_comment (community_id, user_id, content, created_at, updated_at) " +
            "VALUES (#{communityId}, #{user_id}, #{content}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CommentDTO commentDTO);

    @Update("UPDATE community_comment SET content = #{content}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(CommentDTO commentDTO);

    @Delete("DELETE FROM community_comment WHERE id = #{id}")
    int delete(Long id);

    @Select("SELECT COUNT(*) FROM community_comment WHERE community_id = #{communityId}")
    int countByCommunityId(Long communityId);
}