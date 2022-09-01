package com.atguigu.yygh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.user.Patient;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hefei
 * @create 2022-08-27-11:00
 */
@Mapper
public interface PatientMapper extends BaseMapper<Patient> {
}
