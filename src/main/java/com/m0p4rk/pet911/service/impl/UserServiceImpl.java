package com.m0p4rk.pet911.service.impl;

import com.m0p4rk.pet911.dto.LoginDTO;
import com.m0p4rk.pet911.dto.RegisterDTO;
import com.m0p4rk.pet911.dto.UserDTO;
import com.m0p4rk.pet911.dto.UserSessionDTO;
import com.m0p4rk.pet911.mapper.UserMapper;
import com.m0p4rk.pet911.mapper.util.UserMapperUtil;
import com.m0p4rk.pet911.model.User;
import com.m0p4rk.pet911.service.UserService;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private HttpSession session;

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

    @Override
    public boolean authenticateUser(LoginDTO loginDTO) {
        User user = userMapper.findByEmail(loginDTO.getEmail());
        if (user != null && passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            UserSessionDTO userSession = new UserSessionDTO(user.getId(), user.getUsername(), user.getEmail());
            session.setAttribute("user", userSession); // 세션에 필요한 정보만 저장
            return true;
        }
        return false;
    }


    public void logoutUser() {
        session.invalidate(); // 세션 무효화
    }
}
