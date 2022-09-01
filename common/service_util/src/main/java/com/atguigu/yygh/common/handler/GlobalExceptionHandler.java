package com.atguigu.yygh.common.handler;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Hefei
 * @create 2022-08-08-16:19
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    /**
     * @description: 自定义异常处理方法
     * @param : e
     */
    @ExceptionHandler(YyghException.class)
    @ResponseBody
    public Result error(YyghException e){

        return Result.build(e.getCode(),e.getMessage());
    }
}
