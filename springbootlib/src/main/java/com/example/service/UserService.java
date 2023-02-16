package com.example.service;

import com.example.controller.request.UserPageRequest;
import com.example.pojo.User;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface UserService {
    List<User> list();

    PageInfo<User> page(UserPageRequest userPageRequest);

    int save(User user);

    int edit(User user);

    User getById(Integer id);

    int deleteById(Integer id);

    void handleAccount(User user);
}
