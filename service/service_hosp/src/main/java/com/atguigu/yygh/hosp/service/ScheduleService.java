package com.atguigu.yygh.hosp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import model.hosp.Schedule;
import vo.hosp.ScheduleOrderVo;
import vo.hosp.ScheduleQueryVo;

import java.util.List;
import java.util.Map;

/**
 * @author Hefei
 * @create 2022-08-23-11:13
 */
public interface ScheduleService{
    void save(Map<String, Object> paramMap);

    Page<Schedule> selectPage(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    void remove(String hoscode, String hosScheduleId);

    //根据医院编号 和 科室编号 ，查询排班规则数据
    Map<String, Object> getRuleSchedule(long page, long limit, String hoscode, String depcode);

    //根据医院编号 、科室编号和工作日期，查询排班详细信息
    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);

    /**
     * @description: 获取排版可预约日期数据
     * @param null: page、limit、hoscode（医院id）、depcode（部门id）
     * @return
     */
    Map<String,Object> getBookingScheduleRule(int page,int limit,String hoscode,String depcode);

    Schedule getById(String scheduleId);

    ScheduleOrderVo getScheduleOrderVo(String scheduleId);

    /**
     * 修改排班
     */
    void update(Schedule schedule);

    Schedule getScheduleId(String scheduleId);
}
