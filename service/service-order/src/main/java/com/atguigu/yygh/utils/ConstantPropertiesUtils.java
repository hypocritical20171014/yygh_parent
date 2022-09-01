package com.atguigu.yygh.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**引入工具类来读取weixin支付的配置信息
 * @author Hefei
 * @create 2022-08-31-17:09
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {


    @Value("${weixin.appid}")
    private String appid;

    @Value("${weixin.partner}")
    private String partner;

    @Value("${weixin.partnerkey}")
    private String partnerkey;

    @Value("${weixin.cert}")
    private String cert;

    public static String APPID;
    public static String PARTNER;
    public static String PARTNERKEY;
    public static String CERT;
    @Override
    public void afterPropertiesSet() throws Exception {
        APPID = appid;
        PARTNER = partner;
        PARTNERKEY = partnerkey;
        CERT = cert;

    }
}
