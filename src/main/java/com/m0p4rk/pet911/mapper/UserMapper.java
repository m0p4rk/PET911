package com.m0p4rk.pet911.mapper;

import com.m0p4rk.pet911.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    User findById(Long id);
    List<User> findAll();
    void insert(User user);
    void update(User user);
    void delete(Long id);

}
