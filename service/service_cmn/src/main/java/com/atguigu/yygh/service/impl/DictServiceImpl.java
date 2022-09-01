package com.atguigu.yygh.service.impl;
import java.util.Arrays;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.atguigu.yygh.listener.DictListener;
import com.atguigu.yygh.mapper.DictMapper;
import com.atguigu.yygh.service.DictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import model.cmn.Dict;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vo.cmn.DictEeVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hefei
 * @create 2022-08-17-20:42
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Autowired
    DictMapper dictMapper;

    //根据数据ID查询子数据列表
    @Override
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        //向每个list集合每个dict对象中设置hashChildern。判断是否包含子节点
        for(Dict dict:dictList){
            Long dictId = dict.getId();
            boolean isChild = this.isChildren(dictId);
            dict.setHasChildren(isChild);
        }

        return dictList;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try {
            //设置下载信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("数据字典", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");
            //查询数据库
            List<Dict> dictList = baseMapper.selectList(null);
            //Dict --复制到这里 DictEeVo
            List<DictEeVo> dictVoList = new ArrayList<>(dictList.size());
            for(Dict dict : dictList) {
                DictEeVo dictVo = new DictEeVo();
                BeanUtils.copyProperties(dict, dictVo, DictEeVo.class);
                dictVoList.add(dictVo);
            }
            //调用方法进行写操作
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict")
                    .doWrite(dictVoList);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入
     * allEntries = true: 方法调用后清空所有缓存
     * @param file
     */
    @Override
    @CacheEvict(value = "dict", allEntries=true)
    public void importDictData(MultipartFile file){
        try {
            EasyExcel.read(file.getInputStream(),DictEeVo.class,new DictListener(dictMapper))
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @description: 根据parent id 和 id 返回name
     * //如果value能唯一定位数据字典，parentDictCode可以传空，例如：省市区的value值能够唯一确定
     * @param parentDictCode: parentId
     * @param value: ID
     * @return java.lang.String
     */
    @Override
    public String getNameByParentDictCodeAndValue(String parentDictCode, String value) {
        if(StringUtils.isEmpty(parentDictCode)) {
            Dict dict = dictMapper.selectOne(new QueryWrapper<Dict>().eq("value", value));
            if(null != dict) {
                return dict.getName();
            }
        } else {
            Dict parentDict = this.getByDictCode(parentDictCode);
            if(null == parentDict) return "";
            Dict dict = dictMapper.selectOne(new QueryWrapper<Dict>().eq("parent_id", parentDict.getId()).eq("value", value));
            if(null != dict) {
                return dict.getName();
            }
        }
        return "";
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {
        Dict codeDict = this.getDictByDictCode(dictCode);
        if(null == codeDict) return null;
        return this.findChildData(codeDict.getId());
    }

    private Dict getDictByDictCode(String dictCode) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code",dictCode);
        Dict dict = baseMapper.selectOne(wrapper);
        return dict;
    }

    private Dict getByDictCode(String parentDictCode) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",parentDictCode);
        Dict dict = baseMapper.selectOne(wrapper);
        return dict;
    }

    //判断id下面是否有子节点
    private boolean isChildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }
}
