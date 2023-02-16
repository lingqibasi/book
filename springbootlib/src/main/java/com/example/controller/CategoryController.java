package com.example.controller;


import cn.hutool.core.collection.CollUtil;
import com.example.controller.request.CategoryPageRequest;
import com.example.pojo.Category;
import com.example.restful.Result;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RequestMapping("/category")
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public Result list() {
        List<Category> categoryList = categoryService.list();
        return Result.success(categoryList);
    }

    @GetMapping("/tree")
    public Result tree() {
        List<Category> categoryList = categoryService.list();

        //!!!
//        List<Category> treeList = categoryList.stream().filter(v -> v.getPid() == null).collect(Collectors.toList());

        return Result.success(createTree(null, categoryList));   //  null 表示从第一级开始递归
    }

    // 完全递归的方法来实现递归树
    private List<Category> createTree(Integer pid, List<Category> categories) {
        List<Category> treeList = new ArrayList<>();
        for (Category category : categories) {
            if (pid == null) {
                if (category.getPid() == null) {  // 那这就是第一级节点
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(), categories));
                }
            } else {
                if (pid.equals(category.getPid())) {
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(), categories));
                }
            }
            if (CollUtil.isEmpty(category.getChildren())) {
                category.setChildren(null);
            }
        }
        return treeList;
    }

    @GetMapping("/page")
    public Result getPage(CategoryPageRequest categoryPageRequest) {
        Object page = categoryService.page(categoryPageRequest);
        return Result.success(page);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Category category) {
        categoryService.save(category);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Category byId = categoryService.getById(id);
        if (byId != null) {
            return Result.success(byId);
        }
        return Result.error("查询失败");
    }

    @PutMapping("/edit")
    public Result edit(@RequestBody Category category) {
        int edit = categoryService.edit(category);
        if (edit < 1) {
            return Result.error("修改失败");
        }
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        int delete = categoryService.deleteById(id);
        if (delete < 1) {
            return Result.error("删除失败");
        }
        return Result.success();
    }
}
