package com.example.service;

import com.example.controller.request.BasePageRequest;
import com.example.controller.request.BorrowPageRequest;
import com.example.pojo.Back;
import com.example.pojo.Borrow;
import com.github.pagehelper.PageInfo;

public interface BackService {

    PageInfo<Back> page(BasePageRequest basePageRequest);

    void save(Back back);

    int deleteById(Integer id);
}
