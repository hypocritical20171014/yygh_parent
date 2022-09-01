package com.atguigu.yygh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.order.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

/**交易记录接口
 * @author Hefei
 * @create 2022-08-31-17:14
 */
@Mapper
public interface PaymentInfoMapper extends BaseMapper<PaymentInfo> {
}
