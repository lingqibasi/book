package com.example.service;

import com.example.controller.request.BasePageRequest;
import com.example.controller.request.BorrowPageRequest;
import com.example.pojo.Borrow;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface BorrowService {


    List<Borrow> list();

    PageInfo<Borrow> page(BorrowPageRequest borrowPageRequest);

    void save(Borrow borrow);

    int deleteById(Integer id);

    void edit(Borrow borrow);

    Borrow getById(Integer id);


    Map<String,Object> getCountByTimeRange(String timeRange);
}
