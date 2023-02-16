package com.example.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Integer id;
    private String username;
    private String email;
    private String phone;
    private String token;
}
