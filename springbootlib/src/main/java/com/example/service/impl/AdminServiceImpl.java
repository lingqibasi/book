package com.example.service.impl;

import com.example.controller.request.AdminPageRequest;
import com.example.controller.request.LoginRequest;
import com.example.controller.request.PasswordRequest;
import com.example.dto.LoginDTO;
import com.example.exception.ServiceException;
import com.example.mapper.AdminMapper;
import com.example.pojo.Admin;
import com.example.service.AdminService;
import com.example.utils.Solt;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    private static final String DEFAULT_PASSWORD = "123456";
    private static final String PASSWORD_SALT = "zjl";

    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    @Override
    public PageInfo<Admin> page(AdminPageRequest adminPageRequest) {

        PageHelper.startPage(adminPageRequest.getPageNum(), adminPageRequest.getPageSize());
        List<Admin> adminList = adminMapper.ListByCondition(adminPageRequest);
        PageInfo<Admin> adminPageInfo = new PageInfo<>(adminList);

        return adminPageInfo;
    }

    @Override
    public void save(Admin admin) {
        if (admin.getPassword() == null) {
            admin.setPassword(DEFAULT_PASSWORD);
        }
        admin.setPassword(Solt.secureUtil(admin.getPassword(), PASSWORD_SALT));
        try {
            adminMapper.save(admin);
        } catch (DuplicateKeyException e) {
            log.error("数据插入失败,username{}",admin.getUsername());
            throw new ServiceException("用户名重复");
        }

    }

    @Override
    public int edit(Admin admin) {
        admin.setUpdatetime(new Date());
//        System.out.println("admin"+admin);
        return adminMapper.edit(admin);
    }

    @Override
    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return adminMapper.deleteById(id);
    }

    @Override
    public LoginDTO login(LoginRequest loginRequest) {

//        根据用户名查询是否存在
        Admin admin = null;
        try {
            admin = adminMapper.getByUsername(loginRequest.getUsername());
        } catch (Exception e) {
            log.error("查询用户名出错", loginRequest.getUsername());
            throw new ServiceException("用户名错误");
        }

//            将登陆页面发送的密码加密
//        loginRequest.setPassword(Solt.secureUtil(loginRequest.getPassword(), PASSWORD_SALT));
//            System.out.println(loginRequest);
//            查询数据库--根据用户名密码
//            Admin admin = adminMapper.getAdminByUsernameAndPassword(loginRequest.getUsername(),loginRequest.getPassword());
        if (admin == null) {
            throw new ServiceException("用户名或密码错误");
        }

        String password = Solt.secureUtil(loginRequest.getPassword(), PASSWORD_SALT);
        if (!password.equals(admin.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        if (!admin.isStatus()) {
            throw new ServiceException("当前用户被禁用,请联系管理员");
        }
        //            创建返回结果集
        LoginDTO loginDTO = new LoginDTO();
//            将查询的结果复制到结果集中
        BeanUtils.copyProperties(admin, loginDTO);
//            创建token
        String token = TokenUtils.genToken(String.valueOf(admin.getId()), admin.getPassword());
//            将token设置到返回结果集中
        loginDTO.setToken(token);
        return loginDTO;

    }

    @Override
    public int editPassword(PasswordRequest obj) {
        obj.setNewPass(Solt.secureUtil(obj.getNewPass(), PASSWORD_SALT));
//        System.out.println("obj"+obj);
        int count = adminMapper.editPassword(obj);
        if (count <= 0) {
            throw new ServiceException("修改密码失败");
        }
        return adminMapper.editPassword(obj);
    }

    @Override
    public Admin getByUsername(String username) {
        return null;
    }

}
