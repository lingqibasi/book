package com.example.controller;


import com.example.controller.request.BorrowPageRequest;
import com.example.pojo.Back;
import com.example.pojo.Borrow;
import com.example.restful.Result;
import com.example.service.BackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/back")
public class BackController {

    @Autowired
    BackService backService;

    @GetMapping("/page")
    public Result getPage(BorrowPageRequest borrowPageRequest) {
        Object page = backService.page(borrowPageRequest);
        return Result.success(page);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Back back) {
        backService.save(back);
        return Result.success();
    }


    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        int delete = backService.deleteById(id);
        if (delete < 1) {
            return Result.error("删除失败");
        }
        return Result.success();
    }


}
