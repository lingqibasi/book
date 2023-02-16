package com.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.controller.request.BasePageRequest;
import com.example.controller.request.BorrowPageRequest;
import com.example.exception.ServiceException;
import com.example.mapper.BookMapper;
import com.example.mapper.BorrowMapper;
import com.example.mapper.UserMapper;
import com.example.mapper.po.BorrowReturCountPO;
import com.example.pojo.Book;
import com.example.pojo.Borrow;
import com.example.pojo.User;
import com.example.service.BorrowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;


@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    BorrowMapper borrowMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BookMapper bookMapper;

    @Override
    public List<Borrow> list() {
        return borrowMapper.list();
    }

    @Override
    public PageInfo<Borrow> page(BorrowPageRequest borrowPageRequest) {
        System.out.println("借书"+borrowPageRequest);
        PageHelper.startPage(borrowPageRequest.getPageNum(),borrowPageRequest.getPageSize());
        List<Borrow> list = borrowMapper.listByCondition(borrowPageRequest);
        for (Borrow borrow : list) {
            LocalDateTime returnDate = borrow.getReturnDate();
            LocalDateTime now = LocalDateTime.now();
//            当前的日期比归还的日期小一天,
            if (now.plusDays(1).isEqual(returnDate)){
                borrow.setNote("即将到期");
            }else if (now.isEqual(returnDate)){
                borrow.setNote("已到期");
            }else if(now.isAfter(returnDate)){
                borrow.setNote("已过期");
            }else{
                borrow.setNote("正常");
            }
        }
        PageInfo<Borrow> borrowPageInfo = new PageInfo<>(list);

        return borrowPageInfo;
    }

    @Override
    @Transactional
    public void save(Borrow borrow) {
        //        校验积分是否足够
        User user = userMapper.getByNo(borrow.getUserNo());
        if (user == null){
            throw new ServiceException("用户不存在");
        }
//        校验图书数量是否足够
        Book book = bookMapper.getByNo(borrow.getBookNo());
        if (book == null){
            throw new ServiceException("所借图书不存在");
        }
        Integer account = user.getAccount();
//        要计算积分，要乘以借书的天数
        Integer score = book.getScore() * borrow.getDays();
        if (score > account){
            throw new ServiceException("用户积分余额不足");
        }
        if (book.getNums() < 1){
            throw new ServiceException("图书库存不足");
        }
        user.setAccount(user.getAccount() - score);
        System.out.println("更新余额"+user);
        try {
            userMapper.edit(user);
        } catch (Exception e) {
            throw new ServiceException("更新余额异常");
        }
//        更新图书数量
        book.setNums(book.getNums() - 1);
        borrow.setScore(score);
        bookMapper.edit(book);

//        计算归还的日期 用当前时间加上days 为归还日期
        borrow.setReturnDate(LocalDateTime.now().plus(borrow.getDays(),ChronoUnit.DAYS));

        borrowMapper.save(borrow);

    }

    @Override
    public int deleteById(Integer id) {
        return borrowMapper.deleteById(id);
    }

    @Override
    public void edit(Borrow borrow) {

        borrowMapper.edit(borrow);
    }

    @Override
    public Borrow getById(Integer id) {
        return borrowMapper.getById(id);
    }

    @Override
    public Map<String, Object> getCountByTimeRange(String timeRange) {
        Map<String, Object> map = new HashMap<>();
        Date today = new Date();
        List<DateTime> dateRange;
        switch (timeRange) {
            case "week":
                // offsetDay 计算时间的一个工具方法
                // rangeToList 返回从开始时间到结束时间的一个时间范围
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -6), today, DateField.DAY_OF_WEEK);
                break;
            case "month":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -29), today, DateField.DAY_OF_MONTH);
                break;
            case "month2":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -59), today, DateField.DAY_OF_MONTH);
                break;
            case "month3":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -89), today, DateField.DAY_OF_MONTH);
                break;
            default:
                dateRange = new ArrayList<>();
        }
        //  datetimeToDateStr() 就是一个处理的方法， 把 DateTime类型的List转换成一个 String的List
        List<String> dateStrRange = datetimeToDateStr(dateRange);
        map.put("date", dateStrRange);  // x轴的日期数据生产完毕

        //  timeRange = week  month
        // getCountByTimeRange 不会统计数据库没有的日期，比如 数据库 11.4 这一天数据没有，他不会返回 date=2022-11-04,count=0 这个数据
        List<BorrowReturCountPO> borrowCount = borrowMapper.getCountByTimeRange(timeRange, 1);   // 1 borrow  2 return
        List<BorrowReturCountPO> returnCount = borrowMapper.getCountByTimeRange(timeRange, 2);
        map.put("borrow", countList(borrowCount, dateStrRange));
        map.put("retur", countList(returnCount, dateStrRange));
        return map;
    }

    // 把 DateTime类型的List转换成一个 String的List
    private List<String> datetimeToDateStr(List<DateTime> dateTimeList) {
        List<String> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(dateTimeList)) {
            return list;
        }
        for (DateTime dateTime : dateTimeList) {
            String date = DateUtil.formatDate(dateTime);
            list.add(date);
        }
        return list;
    }

    // 对数据库未统计的时间进行处理
    private List<Integer> countList(List<BorrowReturCountPO> countPOList, List<String> dateRange) {
        List<Integer> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(countPOList)) {
            return list;
        }
        for (String date : dateRange) {
            // .map(BorrowReturCountPO::getCount) 取出 对象里的 count值
            // orElse(0) 对没匹配的数据返回0
            // "2022-11-04" 没有的话 就返回0
            Integer count = countPOList.stream().filter(countPO -> date.equals(countPO.getDate()))
                    .map(BorrowReturCountPO::getCount).findFirst().orElse(0);
            list.add(count);
        }
        // 最后返回的list的元素个数会跟 dateRange 的元素个数完全一样
        // dateRange: [
        //            "2022-10-30",
        //            "2022-10-31",
        //            "2022-11-01",
        //            "2022-11-02",
        //            "2022-11-03",
        //            "2022-11-04",
        //            "2022-11-05"
        //        ],
        // borrow: [
        //            0,
        //            0,
        //            2,
        //            1,
        //            0,
        //            1,
        //            3
        //        ]
        return list;
    }

}

