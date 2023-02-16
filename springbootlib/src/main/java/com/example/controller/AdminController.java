package com.example.controller;

import cn.hutool.crypto.SecureUtil;
import com.example.controller.request.AdminPageRequest;
import com.example.controller.request.LoginRequest;
import com.example.controller.request.PasswordRequest;
import com.example.dto.LoginDTO;
import com.example.pojo.Admin;
import com.example.restful.Result;
import com.example.service.AdminService;
import com.example.utils.Solt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        LoginDTO login = adminService.login(loginRequest);

        return Result.success(login);

    }

    @GetMapping("/list")
    public Result listUser() {
        List<Admin> userList = adminService.list();
        return Result.success(userList);
    }

    @GetMapping("/page")
    public Result getPage(AdminPageRequest adminPageRequest) {
        Object page = adminService.page(adminPageRequest);
        return Result.success(page);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Admin admin) {
        adminService.save(admin);
        return Result.success(admin);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Admin byId = adminService.getById(id);
        if (byId != null) {
            return Result.success(byId);
        }
        return Result.error("查询失败");
    }

    @PutMapping("/edit")
    public Result edit(@RequestBody Admin admin) {
        int edit = adminService.edit(admin);
        if (edit < 1) {
            return Result.error("修改失败");
        }
        return Result.success();
    }

    @PutMapping("/password")
    public Result editPassword(@RequestBody PasswordRequest obj) {
        adminService.editPassword(obj);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        int delete = adminService.deleteById(id);
        if (delete < 1) {
            return Result.error("删除失败");
        }
        return Result.success();
    }

}
