package com.example.mapper;

import com.example.controller.request.BasePageRequest;
import com.example.mapper.po.BorrowReturCountPO;
import com.example.pojo.Book;
import com.example.pojo.Borrow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface BorrowMapper {
    List<Borrow> list();

    List<Borrow> listByCondition(BasePageRequest basePageRequest);

    void save(Borrow borrow);

    int deleteById(Integer id);

    void edit(Borrow borrow);

    Borrow getById(Integer id);

    void updateStatus(String status,Integer id);

    Borrow getByNo(String bookNo);


    List<BorrowReturCountPO> getCountByTimeRange(@Param("timeRange") String timeRange, @Param("type") int type);  // 1 borrow  2 return
}
