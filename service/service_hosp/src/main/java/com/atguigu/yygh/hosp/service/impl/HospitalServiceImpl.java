package com.atguigu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.DictFeignClient;
import com.atguigu.yygh.hosp.respository.HospitalRespository;
import com.atguigu.yygh.hosp.service.HospitalService;
import enums.DictEnum;
import model.hosp.Hospital;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vo.hosp.HospitalQueryVo;

import java.util.*;

/**
 * @author Hefei
 * @create 2022-08-22-20:11
 */
@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRespository hospitalRespository;
    @Autowired
    private DictFeignClient dictFeignClient;


    @Override
    public void save(Map<String, Object> paraMap) {
        //把参数map集合转换为对象
        String mapString = JSONObject.toJSONString(paraMap);
        Hospital hospital = JSONObject.parseObject(mapString, Hospital.class);
        //判断是否存在数据
        String hoscode = hospital.getHoscode();
        Hospital hospital1Exist = hospitalRespository.getHospitalByHoscode(hoscode);
        //不存在，则添加
        if(hospital1Exist != null){
            hospital.setStatus(hospital1Exist.getStatus());
            hospital.setCreateTime(hospital1Exist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRespository.save(hospital);
        }else{
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRespository.save(hospital);
        }
        //如果存储，进行修改
    }

    @Override
    public Hospital getByHoscode(String hoscode) {
        return hospitalRespository.getHospitalByHoscode(hoscode);
    }

    @Override
    public Page<Hospital> selectPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        //0为第一页
        Pageable pageable = PageRequest.of(page-1,limit,sort);
        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo,hospital);
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)//模糊查询
                .withIgnoreCase(true);//忽略大小写
        Example<Hospital> ex = Example.of(hospital, matcher);
        Page<Hospital> pages = hospitalRespository.findAll(ex, pageable);
        pages.getContent().stream().forEach(item -> {
            this.packHospital(item);
        });

        return pages;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        if(status.intValue() == 0 || status.intValue() == 1) {
            Hospital hospital = hospitalRespository.findById(id).get();
            hospital.setStatus(status);
            hospital.setUpdateTime(new Date());
            hospitalRespository.save(hospital);
        }
    }

    @Override
    public Map<String, Object> show(String id) {
        Map<String, Object> result = new HashMap<>();

        Hospital hospital = this.packHospital(this.getById(id));
        result.put("hospital", hospital);

        //单独处理更直观
        result.put("bookingRule", hospital.getBookingRule());
        //不需要重复返回
        hospital.setBookingRule(null);
        return null;
    }

    @Override
    public String getHospName(String hoscode) {
        Hospital hospital = hospitalRespository.getHospitalByHoscode(hoscode);
        if( null != hospital){
            return hospital.getHosname();
        }
        return "";
    }

    @Override
    public Map<String, Object> item(String hoscode) {
        Map<String, Object> result = new HashMap<>();
        //医院详情
        Hospital hospital = this.setHospitalHosType(this.getByHoscode(hoscode));
        result.put("hospital", hospital);
        //预约规则
        result.put("bookingRule", hospital.getBookingRule());
        //不需要重复返回
        hospital.setBookingRule(null);
        return result;
    }

    @Override
    public List<Hospital> findByHosname(String hosname) {
        return hospitalRespository.findHospitalByHosnameLike(hosname);
    }

    private Hospital setHospitalHosType(Hospital byHoscode) {
        return new Hospital();
    }

    private Hospital getById(String id) {
        return hospitalRespository.findById(id).get();
    }

    /**
     * 封装数据
     * @param hospital
     * @return
     */
    private Hospital packHospital(Hospital hospital) {
        String hostypeString = dictFeignClient.getName(DictEnum.HOSTYPE.getDictCode(),hospital.getHostype());
        String provinceString = dictFeignClient.getName(hospital.getProvinceCode());
        String cityString = dictFeignClient.getName(hospital.getCityCode());
        String districtString = dictFeignClient.getName(hospital.getDistrictCode());

        hospital.getParam().put("hostypeString", hostypeString);
        hospital.getParam().put("fullAddress", provinceString + cityString + districtString + hospital.getAddress());
        return hospital;
    }
}
