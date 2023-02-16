package com.example.controller.request;

import lombok.Data;

@Data
public class BorrowPageRequest extends BasePageRequest{

    private String bookName;

    private String bookNo;

    private String userName;
}
