package com.example.mapper;

import com.example.controller.request.BasePageRequest;
import com.example.pojo.Back;
import com.example.pojo.Borrow;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BackMapper {

    List<Back> listByCondition(BasePageRequest basePageRequest);

    void save(Back back);

    int deleteById(Integer id);
}
