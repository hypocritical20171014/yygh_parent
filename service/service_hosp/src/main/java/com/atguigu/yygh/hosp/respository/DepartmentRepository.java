package com.atguigu.yygh.hosp.respository;

import model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hefei
 * @create 2022-08-22-22:55
 */
@Repository
public interface DepartmentRepository extends MongoRepository<Department,String> {
    Department getDepartmentByHoscodeAndDepcode(String departmentHoscode, String hoscode);

    List<Department> getDepartmentByHoscode(String hoscode);
}
