package com.m0p4rk.pet911.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmerGuideController {

    @GetMapping("/schemerguides")
    public String searchPage() {
        return "emergencyguide";
    }
}
