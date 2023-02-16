package com.example.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.controller.request.UserPageRequest;
import com.example.pojo.User;
import com.example.restful.Result;
import com.example.service.UserService;
import com.example.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public Result listUser() {
        List<User> userList = userService.list();
        return Result.success(userList);
    }
    @PostMapping("/account")
    public Result account(@RequestBody User user) {
        userService.handleAccount(user);
        return Result.success();
    }

    @GetMapping("/page")
    public Result getPage(UserPageRequest userPageRequest) {
        Object page = userService.page(userPageRequest);
        return Result.success(page);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public Result upload(@RequestPart MultipartFile file) throws IOException {
        String url = FileUtil.uploads(file);
        User user = new User();
        user.setImg(url);
//        userService.save(user);
        return Result.success(user);
    }

    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        int res = userService.save(user);
        if (res >= 1){
            return Result.success(user);
        }else{
            return Result.error("添加失败");
        }
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        User byId = userService.getById(id);
        if (byId != null){
            return Result.success(byId);
        }
        return Result.error("修改失败");
    }

    @PutMapping("/edit")
    public Result edit(@RequestBody User user){
        int edit = userService.edit(user);
        if (edit < 1){
            return Result.error("修改失败");
        }
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        int delete = userService.deleteById(id);
        if (delete < 1){
            return Result.error("删除失败");
        }
        return Result.success();
    }

}
