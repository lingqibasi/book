package com.example.mapper;

import com.example.controller.request.BasePageRequest;
import com.example.pojo.Book;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BookMapper {

    List<Book> list();

    List<Book> listByCondition(BasePageRequest basePageRequest);

    void save(Book book);

    int deleteById(Integer id);

    void edit(Book book);

    Book getById(Integer id);


    Book getByNo(String bookNo);

    void updateNumByNo(String bookNo);
}
