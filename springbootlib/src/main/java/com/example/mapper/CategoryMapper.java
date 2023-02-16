package com.example.mapper;

import com.example.controller.request.BasePageRequest;
import com.example.controller.request.PasswordRequest;
import com.example.pojo.Admin;
import com.example.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface CategoryMapper {

      List<Category> list();

      List<Category> ListByCondition(BasePageRequest basePageRequest);

      void save(Category category);

      int edit(Category category);

      Category getById(Integer id);

      int deleteById(Integer id);

      List<Category> selectByPid(Integer id);

}