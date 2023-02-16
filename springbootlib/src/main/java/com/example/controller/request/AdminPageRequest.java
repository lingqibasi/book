package com.example.controller.request;

import lombok.Data;

@Data
public class AdminPageRequest extends BasePageRequest{
    private String username;
    private String phone;
    private String email;
}
