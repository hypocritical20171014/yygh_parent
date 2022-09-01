package com.atguigu.yygh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import model.user.Patient;

import java.util.List;

/**
 * @author Hefei
 * @create 2022-08-27-11:01
 */
public interface PatientService extends IService<Patient> {
    //获取就诊人列表
    List<Patient> findAllUserId(Long userId);
    //根据id获取就诊人信息
    Patient getPatientId(Long id);
}
