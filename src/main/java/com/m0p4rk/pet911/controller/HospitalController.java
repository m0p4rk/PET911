package com.m0p4rk.pet911.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HospitalController {

    @Value("${api.key.kakao.js}")
    private String kakaoApiKey;

    @GetMapping("/schhospitals")
    public String searchPage(Model model) {
        model.addAttribute("kakaoApiKey", kakaoApiKey);
        return "hospitalSearch";
    }
}
