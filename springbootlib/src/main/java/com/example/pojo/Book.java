package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Book {
    private Integer id;
    private String name;
    private String description;
    private String publishDate;
    private String author;
    private String publicer;
    private String category;
    private String bookNo;
    private String cover;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private List<String> categories;

    private Integer score;

    private Integer nums;
}
