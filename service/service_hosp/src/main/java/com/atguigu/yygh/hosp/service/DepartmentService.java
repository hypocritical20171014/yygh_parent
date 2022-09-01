package com.atguigu.yygh.hosp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import model.hosp.Department;
import vo.hosp.DepartmentQueryVo;
import vo.hosp.DepartmentVo;

import java.util.List;
import java.util.Map;

/**
 * @author Hefei
 * @create 2022-08-22-22:56
 */
public interface DepartmentService {
    void save(Map<String, Object> paramMap);

    Page<Department> selectPage(int page, int limit, DepartmentQueryVo departmentQueryVo);

    void remove(String hoscode, String depcode);

    List<DepartmentVo> findDeptTree(String hoscode);

    String getDepName(String hoscode, String depcode);

    Department getDepartment(String hoscode, String depcode);

    List<Department> findTree(String hoscode);
}
