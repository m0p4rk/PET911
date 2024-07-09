package com.m0p4rk.pet911.controller;

import com.m0p4rk.pet911.dto.UserSessionDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @GetMapping("/user")
    public UserSessionDTO getCurrentUser(HttpSession session) {
        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        return user != null ? user : new UserSessionDTO();
    }
}