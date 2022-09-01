package com.atguigu.yygh.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hefei
 * @create 2022-08-09-21:00
 */
@Configuration
@MapperScan("com.atguigu.yygh.mapper")
public class DictConfig {

    //分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
