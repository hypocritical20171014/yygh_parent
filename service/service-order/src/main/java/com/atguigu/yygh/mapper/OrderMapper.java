package com.atguigu.yygh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import vo.order.OrderCountQueryVo;
import vo.order.OrderCountVo;

import java.util.List;

/**统计的数据都来自订单模块，
 * @author Hefei
 * @create 2022-08-29-20:58
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderInfo> {
    List<OrderCountVo> selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);
}
