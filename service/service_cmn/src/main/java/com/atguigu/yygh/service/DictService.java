package com.atguigu.yygh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Hefei
 * @create 2022-08-17-20:41
 */
public interface DictService extends IService<Dict> {
    //根据数据id 查询子数据列表findChildData
    List<Dict> findChildData(Long id);

    //导出数据字典数据
    void exportData(HttpServletResponse response);

    //导入数据字典的数据
    void importDictData(MultipartFile file);

    String getNameByParentDictCodeAndValue(String parentDictCode, String value);

    List<Dict> findByDictCode(String dictCode);
}
