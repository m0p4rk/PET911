package com.m0p4rk.pet911.mapper.util;

import com.m0p4rk.pet911.dto.UserDTO;
import com.m0p4rk.pet911.dto.UserSessionDTO;
import com.m0p4rk.pet911.model.User;

import java.sql.Timestamp;

public class UserMapperUtil {

    public static UserDTO mapToDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setProvider(user.getProvider());
        userDTO.setProviderId(user.getProviderId());
        userDTO.setRole(user.getRole());
        userDTO.setCreatedAt(user.getCreatedAt().toString());
        return userDTO;
    }

    public static User mapToEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        // ID는 자동 증가이므로 수동 설정 불필요
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setProvider(userDTO.getProvider());
        user.setProviderId(userDTO.getProviderId());
        user.setRole(userDTO.getRole());
        // createdAt은 DB에서 자동으로 설정됨
        return user;
    }

    // UserDTO를 UserSessionDTO로 변환
    public static UserSessionDTO mapToSessionDTO(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return new UserSessionDTO(userDTO.getId(), userDTO.getUsername(), userDTO.getEmail());
    }

    // UserSessionDTO를 UserDTO로 변환
    public static UserDTO mapToFullDTO(UserSessionDTO userSessionDTO) {
        if (userSessionDTO == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userSessionDTO.getId());
        userDTO.setUsername(userSessionDTO.getUsername());
        userDTO.setEmail(userSessionDTO.getEmail());
        // 다른 필드들은 null 또는 기본값으로 설정됩니다.
        return userDTO;
    }

    // UserSessionDTO를 User 엔티티로 변환
    public static User mapSessionToEntity(UserSessionDTO userSessionDTO) {
        if (userSessionDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userSessionDTO.getId());
        user.setUsername(userSessionDTO.getUsername());
        user.setEmail(userSessionDTO.getEmail());
        // 다른 필드들은 null 또는 기본값으로 설정됩니다.
        return user;
    }
}