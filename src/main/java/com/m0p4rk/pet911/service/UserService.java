package com.m0p4rk.pet911.service;

import com.m0p4rk.pet911.dto.LoginDTO;
import com.m0p4rk.pet911.dto.RegisterDTO;
import com.m0p4rk.pet911.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findById(Long id);
    List<UserDTO> findAll();
    void saveUser(RegisterDTO registerDTO);
    void updateUser(UserDTO userDTO);
    void deleteUser(Long id);
    boolean authenticateUser(LoginDTO loginDTO);
    void logoutUser();
}
