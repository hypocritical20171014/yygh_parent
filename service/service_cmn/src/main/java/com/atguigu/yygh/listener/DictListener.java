package com.atguigu.yygh.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.yygh.mapper.DictMapper;
import model.cmn.Dict;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import vo.cmn.DictEeVo;

/**
 * @author Hefei
 * @create 2022-08-17-22:37
 */
public class DictListener extends AnalysisEventListener<DictEeVo> {


    private DictMapper dictMapper;

    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    //一行一行读取
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        //调用方法添加数据库
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo,dict);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
