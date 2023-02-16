package com.example.service.impl;

import com.example.controller.request.AdminPageRequest;
import com.example.controller.request.CategoryPageRequest;
import com.example.exception.ServiceException;
import com.example.mapper.AdminMapper;
import com.example.mapper.CategoryMapper;
import com.example.pojo.Admin;
import com.example.pojo.Category;
import com.example.service.CategoryService;
import com.example.utils.Solt;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    @Override
    public PageInfo<Category> page(CategoryPageRequest categoryPageRequest) {

        PageHelper.startPage(categoryPageRequest.getPageNum(), categoryPageRequest.getPageSize());
        List<Category> categoryList = categoryMapper.ListByCondition(categoryPageRequest);
        PageInfo<Category> categoryPageInfo = new PageInfo<>(categoryList);

        return categoryPageInfo;
    }

    @Override
    public void save(Category category) {
         categoryMapper.save(category);
    }

    @Override
    public int edit(Category category) {
        return categoryMapper.edit(category);
    }

    @Override
    public Category getById(Integer id) {
        return categoryMapper.getById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return categoryMapper.deleteById(id);
    }

}
