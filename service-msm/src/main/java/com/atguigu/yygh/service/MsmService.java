package com.atguigu.yygh.service;

import vo.msm.MsmVo;

/**
 * @author Hefei
 * @create 2022-08-27-8:54
 */
public interface MsmService {
    //发送手机验证码
    boolean send(String phone, String code);
    //发送短信
    boolean send(MsmVo msmVo);
}
