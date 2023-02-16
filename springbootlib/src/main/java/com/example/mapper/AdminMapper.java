package com.example.mapper;

import com.example.controller.request.BasePageRequest;
import com.example.controller.request.LoginRequest;
import com.example.controller.request.PasswordRequest;
import com.example.controller.request.UserPageRequest;
import com.example.pojo.Admin;
import com.example.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface AdminMapper {

//    @Select("select * from user")
      List<Admin> list();

      List<Admin> ListByCondition(BasePageRequest basePageRequest);

      int save(Admin admin);

      int edit(Admin admin);

      Admin getById(Integer id);

      int deleteById(Integer id);

      Admin getAdminByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

      int editPassword(PasswordRequest request);

      Admin getByUsername(String username);
}