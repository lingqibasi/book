package com.example.controller;

import com.example.controller.request.BorrowPageRequest;
import com.example.exception.ServiceException;
import com.example.mapper.BookMapper;
import com.example.mapper.UserMapper;
import com.example.pojo.Book;
import com.example.pojo.Borrow;
import com.example.pojo.User;
import com.example.restful.Result;
import com.example.service.BookService;
import com.example.service.BorrowService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    BorrowService borrowService;

    //  timeRange: week month month2 month3
    @GetMapping("/lineCharts/{timeRange}")
    public Result lineCharts(@PathVariable String timeRange) {
        return Result.success(borrowService.getCountByTimeRange(timeRange));
    }

    @GetMapping("/list")
    public Result listUser() {
        List<Borrow> borrowsList = borrowService.list();
        return Result.success(borrowsList);
    }

    @GetMapping("/page")
    public Result getPage(BorrowPageRequest borrowPageRequest) {
        Object page = borrowService.page(borrowPageRequest);
        return Result.success(page);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Borrow borrow) {
        borrowService.save(borrow);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Borrow byId = borrowService.getById(id);
        return Result.success(byId);

    }

    @PutMapping("/edit")
    public Result edit(@RequestBody Borrow borrow) {
        borrowService.edit(borrow);
        return Result.success();
    }
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        int delete = borrowService.deleteById(id);
        if (delete < 1) {
            return Result.error("删除失败");
        }
        return Result.success();
    }
}
