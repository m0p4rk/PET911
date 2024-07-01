package com.m0p4rk.pet911.controller;

import com.m0p4rk.pet911.dto.LoginDTO;
import com.m0p4rk.pet911.dto.RegisterDTO;
import com.m0p4rk.pet911.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("registerDTO") @Valid RegisterDTO registerDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        userService.saveUser(registerDTO);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("loginDTO") @Valid LoginDTO loginDTO, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "login";
        }
        if (userService.authenticateUser(loginDTO)) {
            return "redirect:/";
        } else {
            result.rejectValue("password", "error.loginDTO", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        userService.logoutUser();
        return "redirect:/login";
    }
}
