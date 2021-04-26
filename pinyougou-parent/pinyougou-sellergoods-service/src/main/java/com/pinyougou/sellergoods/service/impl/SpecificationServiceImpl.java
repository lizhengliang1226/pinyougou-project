package com.pinyougou.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojogroup.Specification;
import com.pinyougou.sellergoods.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;


/**
 * 品牌业务逻辑层
 *
 * @author Administrator
 */
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private TbSpecificationMapper tbSpecificationMapper;
    @Autowired
    private TbSpecificationOptionMapper optionMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbSpecification> findAll() {
        return tbSpecificationMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbSpecification> page = (Page<TbSpecification>) tbSpecificationMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(Specification tbSpecification) {
        tbSpecificationMapper.insert(tbSpecification.getSpecification());
        tbSpecification.getSpecificationOptionList().forEach(option -> {
            option.setSpecId(tbSpecification.getSpecification().getId());
            optionMapper.insert(option);
        });
    }


    /**
     * 修改
     */
    @Override
    public void update(Specification tbSpecification) {
        TbSpecification specification = tbSpecification.getSpecification();
        tbSpecificationMapper.updateByPrimaryKey(specification);
        TbSpecificationOptionExample optionExample=new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = optionExample.createCriteria();
        criteria.andSpecIdEqualTo(specification.getId());
        optionMapper.deleteByExample(optionExample);
        tbSpecification.getSpecificationOptionList().forEach(option -> {
            option.setSpecId(tbSpecification.getSpecification().getId());
            optionMapper.insert(option);
        });
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Specification findOne(Long id) {
        Specification specification = new Specification();
        TbSpecification tbSpecification = tbSpecificationMapper.selectByPrimaryKey(id);
        TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<TbSpecificationOption> tbSpecificationOptions = optionMapper.selectByExample(tbSpecificationOptionExample);
        specification.setSpecification(tbSpecification);
        specification.setSpecificationOptionList(tbSpecificationOptions);
        return specification;
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for(Long id : ids) {
            tbSpecificationMapper.deleteByPrimaryKey(id);
            TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
            criteria.andSpecIdEqualTo(id);
            optionMapper.deleteByExample(tbSpecificationOptionExample);
        }
    }

    @Override
    public List<Map> selectSpecificationList() {
        return tbSpecificationMapper.selectSpecificationList();
    }

}
