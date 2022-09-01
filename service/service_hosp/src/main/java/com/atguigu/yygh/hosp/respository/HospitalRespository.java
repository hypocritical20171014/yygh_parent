package com.atguigu.yygh.hosp.respository;

import model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hefei
 * @create 2022-08-22-20:09
 */
@Repository
public interface HospitalRespository extends MongoRepository<Hospital,String> {

    //判断是否存在数据
    Hospital getHospitalByHoscode(String hoscode);

    List<Hospital> findHospitalByHosnameLike(String hosname);
}
