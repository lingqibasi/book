package com.example.service;

import com.example.controller.request.AdminPageRequest;

import com.example.controller.request.LoginRequest;
import com.example.controller.request.PasswordRequest;
import com.example.dto.LoginDTO;
import com.example.pojo.Admin;
import com.example.pojo.User;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface AdminService {
    List<Admin> list();

    PageInfo<Admin> page(AdminPageRequest adminPageRequest);

    void save(Admin admin);

    int edit(Admin admin);

    Admin getById(Integer id);

    int deleteById(Integer id);

    LoginDTO login(LoginRequest loginRequest);

    int editPassword(PasswordRequest obj);

    Admin getByUsername(String username);
}
