package com.m0p4rk.pet911.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String provider;
    private String providerId;
    private String role;
    private Timestamp createdAt;

}
