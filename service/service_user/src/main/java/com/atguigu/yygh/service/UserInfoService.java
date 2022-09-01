package com.atguigu.yygh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import model.user.UserInfo;
import vo.user.LoginVo;
import vo.user.UserAuthVo;
import vo.user.UserInfoQueryVo;

import java.util.Map;

/**
 * @author Hefei
 * @create 2022-08-27-8:14
 */
public interface UserInfoService extends IService<UserInfo> {
    Map<String, Object> login(LoginVo loginVo);

    UserInfo getByOpenid(String openId);
    //用户认证
    //用户登录成功后都要进行身份认证，认证通过后才可以预约挂号
    //认证过程：用户填写信息（姓名、证件类型、证件号码和证件照片）==> 平台审批
    //用户认证设计接口：
        // 1、提交认证
        //2、上传证件图片
        //3、获取提交认证信息
    void userAuth(Long userId, UserAuthVo userAuthVo);

    IPage<UserInfo> selectPage(Page<UserInfo> pageParam, UserInfoQueryVo userInfoQueryVo);

    void lock(Long userId, Integer status);

    Map<String, Object> show(Long userId);

    void approval(Long userId, Integer authStatus);
}
