package com.example.service.impl;

import com.example.controller.request.BasePageRequest;
import com.example.controller.request.BorrowPageRequest;
import com.example.mapper.BackMapper;
import com.example.mapper.BookMapper;
import com.example.mapper.BorrowMapper;
import com.example.mapper.UserMapper;
import com.example.pojo.Back;
import com.example.pojo.Book;
import com.example.pojo.Borrow;
import com.example.pojo.User;
import com.example.service.BackService;
import com.example.service.BorrowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class BackServiceImpl implements BackService {

    @Autowired
    BackMapper backMapper;
    @Autowired
    BorrowMapper borrowMapper;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public PageInfo<Back> page(BasePageRequest basePageRequest) {
        PageHelper.startPage(basePageRequest.getPageNum(), basePageRequest.getPageSize());
        List<Back> backs = backMapper.listByCondition(basePageRequest);
        PageInfo<Back> backPageInfo = new PageInfo<>(backs);
        return backPageInfo;
    }

    @Override
    @Transactional
    public void save(Back back) {
        back.setStatus("已归还");
        borrowMapper.updateStatus("已归还", back.getId());
        back.setRealDate(LocalDateTime.now());

        backMapper.save(back);

        Book book = bookMapper.getByNo(back.getBookNo());

        if (book != null) {
            long until = 0;
            if (back.getRealDate().isBefore(back.getReturnDate())) {
                 until = back.getRealDate().until(back.getReturnDate(), ChronoUnit.DAYS);
            } else if (back.getRealDate().isAfter(back.getReturnDate())) {//逾期归还
                until = -back.getReturnDate().until(back.getRealDate(), ChronoUnit.DAYS);

            }
            int score = (int) until * book.getScore();  // 获取待归还的积分
            User user = userMapper.getByNo(back.getUserNo());
            int account = user.getAccount() + score;
            user.setAccount(account);
            if (account <0){
                if (account < 0){
                    user.setStatus(false);
                }
            }
            userMapper.edit(user);
//            userMapper.updateByUserNo(score, back.getUserNo());

        }


        bookMapper.updateNumByNo(back.getBookNo());

    }

    @Override
    public int deleteById(Integer id) {
        return backMapper.deleteById(id);
    }
}
