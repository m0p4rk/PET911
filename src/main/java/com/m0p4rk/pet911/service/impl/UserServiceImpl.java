package com.m0p4rk.pet911.service.impl;

import com.m0p4rk.pet911.dto.RegisterDTO;
import com.m0p4rk.pet911.dto.UserDTO;
import com.m0p4rk.pet911.mapper.UserMapper;
import com.m0p4rk.pet911.model.User;
import com.m0p4rk.pet911.service.UserService;
import com.m0p4rk.pet911.mapper.util.UserMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO findById(Long id) {
        User user = userMapper.findById(id);
        return UserMapperUtil.mapToDTO(user);
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userMapper.findAll();
        return users.stream().map(UserMapperUtil::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public void saveUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setPhoneNumber(registerDTO.getPhoneNumber());
        user.setRole("USER");
        // createdAt은 자동으로 설정됨
        userMapper.insert(user);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User user = UserMapperUtil.mapToEntity(userDTO);
        userMapper.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.delete(id);
    }
}
