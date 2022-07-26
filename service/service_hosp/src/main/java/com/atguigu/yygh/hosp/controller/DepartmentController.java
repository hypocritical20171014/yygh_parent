package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.hosp.service.DepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vo.hosp.DepartmentVo;

import java.util.List;

/**
 * @author Hefei
 * @create 2022-08-26-16:50
 */
@RestController
@RequestMapping("/admin/hosp/department")
@CrossOrigin
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    //根据医院编号，查询所有科室列表
    @ApiOperation(value = "查询所有科室列表")
    @GetMapping("getDeptList/{hoscode}")
    public Result getDepList(@PathVariable String hoscode){
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.ok(list);
    }
}
