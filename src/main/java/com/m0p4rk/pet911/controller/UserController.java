package com.m0p4rk.pet911.controller;

import com.m0p4rk.pet911.dto.LoginDTO;
import com.m0p4rk.pet911.dto.RegisterDTO;
import com.m0p4rk.pet911.dto.UserDTO;
import com.m0p4rk.pet911.dto.UserSessionDTO;
import com.m0p4rk.pet911.mapper.util.UserMapperUtil;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String loginUser(@ModelAttribute("loginDTO") @Valid LoginDTO loginDTO, BindingResult result) {
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

    @GetMapping("/mypage")
    public String showMyPage(Model model, HttpSession session) {
        Object userObj = session.getAttribute("user");
        if (userObj instanceof UserSessionDTO) {
            UserSessionDTO userSessionDTO = (UserSessionDTO) userObj;
            UserDTO userDTO = UserMapperUtil.mapToFullDTO(userSessionDTO);
            // 필요한 경우 추가 정보를 데이터베이스에서 조회
            model.addAttribute("user", userDTO);
            return "mypage";
        } else if (userObj instanceof UserDTO) {
            // 만약 세션에 UserDTO가 저장되어 있다면
            UserDTO userDTO = (UserDTO) userObj;
            model.addAttribute("user", userDTO);
            return "mypage";
        }
        return "redirect:/login";
    }

    @PostMapping("/mypage/update")
    public String updateUser(@ModelAttribute("user") UserDTO userDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "mypage";
        }
        userService.updateUser(userDTO);
        redirectAttributes.addFlashAttribute("message", "프로필이 성공적으로 업데이트되었습니다.");
        return "redirect:/mypage";
    }
}
