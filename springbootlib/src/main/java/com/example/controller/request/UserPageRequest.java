package com.example.controller.request;

import lombok.Data;

@Data
public class UserPageRequest extends BasePageRequest{
    private String name;
    private String phone;
}
