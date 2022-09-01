package com.atguigu.yygh.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Hefei
 * @create 2022-08-27-10:48
 */
public interface FileService {
    //上传文件到阿里云oss
    String upload(MultipartFile file);
}
