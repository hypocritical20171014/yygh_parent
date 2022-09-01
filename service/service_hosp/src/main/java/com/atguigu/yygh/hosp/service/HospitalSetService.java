package com.atguigu.yygh.hosp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import model.hosp.HospitalSet;
import vo.order.SignInfoVo;

/**
 * @author Hefei
 * @create 2022-08-03-16:28
 */
public interface HospitalSetService extends IService<HospitalSet> {
    //根据传递过来的医院编码，查询数据库，查询签名
    String getSignKey(String hoscode);
    //获取医院签名信息
    SignInfoVo getSignInfoVo(String hoscode);
}
