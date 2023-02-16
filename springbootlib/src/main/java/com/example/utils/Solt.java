package com.example.utils;

import cn.hutool.crypto.SecureUtil;

public class Solt {

    public static String secureUtil(String password,String solt){
        return SecureUtil.md5(password + solt);
    }
}
