package com.atguigu.yygh.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.util.AuthContextHolder;
import com.atguigu.yygh.common.utils.IpUtil;
import com.atguigu.yygh.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import model.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vo.user.LoginVo;
import vo.user.UserAuthVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Hefei
 * @create 2022-08-27-8:15
 */
@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "会员登陆")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo, HttpServletRequest request){
        loginVo.setIp(IpUtil.getIpAddr(request));
        Map<String,Object> info = userInfoService.login(loginVo);
        return Result.ok(info);
    }
    //用户认证接口
    @PostMapping("auth/userAuth")
    public Result userAuth(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request) {
        //传递两个参数，第一个参数用户id，第二个参数认证数据vo对象
        userInfoService.userAuth(AuthContextHolder.getUserId(request),userAuthVo);
        return Result.ok();
    }

    //获取用户id信息接口
    @GetMapping("auth/getUserInfo")
    public Result getUserInfo(HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId(request);
        UserInfo userInfo = userInfoService.getById(userId);
        return Result.ok(userInfo);
    }
}
