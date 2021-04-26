package com.pinyougou.sellergoods.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.16-16:33
 */
@Service
public class BrandServiceImpl implements BrandService {

    private TbBrandMapper tbBrandMapper;

    @Autowired
    public void setTbBrandMapper(TbBrandMapper tbBrandMapper) {
        this.tbBrandMapper = tbBrandMapper;
    }

    @Override
    public List<TbBrand> findAll() {
        return tbBrandMapper.selectByExample(null);
    }

    @Override
    public PageResult findPage(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        Page<TbBrand> page = (Page<TbBrand>) tbBrandMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public boolean addBrand(TbBrand tbBrand) {
        return tbBrandMapper.insert(tbBrand) > 0;
    }

    @Override
    public TbBrand findById(long id) {
        return tbBrandMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean update(TbBrand tbBrand) {
        return tbBrandMapper.updateByPrimaryKey(tbBrand) > 0;
    }

    @Override
    public boolean delete(long[] ids) {
        int i = 0;
        for(long id : ids) {
            i = tbBrandMapper.deleteByPrimaryKey(id);
        }
        return i > 0;
    }

    @Override
    public PageResult findPage(TbBrand tbBrand, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        TbBrandExample tbBrandExample = new TbBrandExample();
        TbBrandExample.Criteria criteria = tbBrandExample.createCriteria();
        if (ObjectUtil.isNotNull(tbBrand)) {
            String name = tbBrand.getName();
            String firstChar = tbBrand.getFirstChar();
            if (StrUtil.isNotEmpty(name)) {
                criteria.andNameLike("%" + name + "%");
            }
            if (StrUtil.isNotEmpty(firstChar)) {
                criteria.andFirstCharEqualTo(firstChar);
            }
        }
        Page<TbBrand> page = (Page<TbBrand>) tbBrandMapper.selectByExample(tbBrandExample);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Map> selectOptionList() {
        return tbBrandMapper.selectOptionList();
    }
}
