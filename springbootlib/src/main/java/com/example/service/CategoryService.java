package com.example.service;

import com.example.controller.request.AdminPageRequest;
import com.example.controller.request.CategoryPageRequest;
import com.example.pojo.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryService {


    List<Category> list();

    PageInfo<Category> page(CategoryPageRequest categoryPageRequest);

    void save(Category category);

    int edit(Category category);

    Category getById(Integer id);

    int deleteById(Integer id);

}
