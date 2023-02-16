package com.example.controller.request;

import lombok.Data;

@Data
public class BookPageRequest extends BasePageRequest{
    private String name;
    private String bookNo;
}
