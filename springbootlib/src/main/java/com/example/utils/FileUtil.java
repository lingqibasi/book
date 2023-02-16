package com.example.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtil {

    public static final String UPLOADS_PATH = "D:/java/image/";

    public static String uploads(MultipartFile file) throws IOException {

        final String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);

        //文件名
        String fileName = UUID.randomUUID().toString().replace("-","") + "." + fileSuffix;
        System.out.println("fileName"+fileName);
        //文件写入
        File descFile = new File(UPLOADS_PATH,fileName);
        file.transferTo(descFile);

//        文件url
        String url = "/image/" + fileName;
        return url;

    }
}
