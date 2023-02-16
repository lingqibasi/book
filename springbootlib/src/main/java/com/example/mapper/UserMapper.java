package com.example.mapper;

import com.example.controller.request.BasePageRequest;
import com.example.controller.request.UserPageRequest;
import com.example.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.util.List;


@Mapper
@Component
public interface UserMapper {

//    @Select("select * from user")
      List<User> list();

      List<User> ListByCondition(BasePageRequest basePageRequest);

      int save(User user);

      int edit(User user);

//    List<User> listByCondition(BaseRequest baseRequest);

    User getById(Integer id);

    int deleteById(Integer id);

    void addAccount(User user);

    User getByNo(String userNo);

    void updateByUserNo(@Param("score") int score,@Param("status") int status, @Param("userNo") String userNo);

//    void updateById(User user);
//
//    void deleteById(Integer id);
}