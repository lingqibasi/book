package com.example.controller.request;

import lombok.Data;

@Data
public class BasePageRequest {
    private Integer pageNum = 1;
    private Integer pageSize = 5;
}
