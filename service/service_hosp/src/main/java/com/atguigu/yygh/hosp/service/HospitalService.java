package com.atguigu.yygh.hosp.service;

import model.hosp.Hospital;
import org.springframework.data.domain.Page;
import vo.hosp.HospitalQueryVo;

import java.util.List;
import java.util.Map;

/**
 * @author Hefei
 * @create 2022-08-22-20:11
 */
public interface HospitalService {

    //上传医院接口方法
    void save(Map<String, Object> paraMap);

    //查询医院
    Hospital getByHoscode(String hoscode);

    //分页查询
    /**
     * @description:
     * @param page: 当前页码
     * @param limit: 每页记录数
     * @param hospitalQueryVo: 查询条件
     * @return org.springframework.data.domain.Page<model.hosp.Hospital>
     */
    Page<Hospital> selectPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    void updateStatus(String id, Integer status);

    Map<String,Object> show(String id);

    String getHospName(String hoscode);

    Map<String,Object> item(String hoscode);

    List<Hospital> findByHosname(String hosname);
}
