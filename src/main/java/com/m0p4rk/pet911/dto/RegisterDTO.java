package com.m0p4rk.pet911.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterDTO {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
}
