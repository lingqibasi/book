package com.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.example.controller.request.BookPageRequest;
import com.example.exception.ServiceException;
import com.example.mapper.BookMapper;
import com.example.pojo.Book;
import com.example.service.BookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

    @Override
    public List<Book> list() {
        return bookMapper.list();
    }

    @Override
    public PageInfo<Book> page(BookPageRequest bookPageRequest) {
        PageHelper.startPage(bookPageRequest.getPageNum(),bookPageRequest.getPageSize());
        List<Book> books = bookMapper.listByCondition(bookPageRequest);
        PageInfo<Book> bookPageInfo = new PageInfo<>(books);
        return bookPageInfo;
    }

    @Override
    public void save(Book book) {
        try {
            book.setCategory(category(book.getCategories()));
            System.out.println(book);
            bookMapper.save(book);
        } catch (Exception e) {
            throw new ServiceException("数据插入错误",e);
        }
    }

    @Override
    public void edit(Book book) {
        try {
            book.setCategory(category(book.getCategories()));
            bookMapper.edit(book);
        } catch (Exception e) {
            throw new ServiceException("数据更新错误",e);
        }
    }

    @Override
    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return bookMapper.deleteById(id);
    }


    private String category(List<String> categories) {
        StringBuilder sb = new StringBuilder();
        if (CollUtil.isNotEmpty(categories)) {
            categories.forEach(v -> sb.append(v).append(" > "));
            return sb.substring(0, sb.lastIndexOf(" > "));
        }
        return sb.toString();
    }
}
