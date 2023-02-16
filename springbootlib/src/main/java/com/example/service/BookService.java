package com.example.service;

import com.example.controller.request.BookPageRequest;
import com.example.pojo.Book;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BookService {

    List<Book> list();

    PageInfo<Book> page(BookPageRequest bookPageRequest);

    void save(Book book);

    void edit(Book book);

    Book getById(Integer id);

    int deleteById(Integer id);
}
