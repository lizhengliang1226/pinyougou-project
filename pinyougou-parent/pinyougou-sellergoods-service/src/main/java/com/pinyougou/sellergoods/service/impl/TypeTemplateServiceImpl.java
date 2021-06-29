package com.pinyougou.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.mapper.TbTypeTemplateMapper;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.pojo.TbTypeTemplateExample;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;


/**
 * 品牌业务逻辑层
 *
 * @author Administrator
 */
@Service
@Transactional
public class TypeTemplateServiceImpl implements TypeTemplateService {

    @Autowired
    private TbTypeTemplateMapper tbTypeTemplateMapper;
    @Autowired
    private TbSpecificationOptionMapper tbSpecificationOptionMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${BRAND_LIST_REDIS_NAME}")
    private String BRAND_LIST_REDIS_NAME;
    @Value("${SPEC_LIST_REDIS_NAME}")
    private String SPEC_LIST_REDIS_NAME;

    /**
     * 查询全部
     */
    @Override
    public List<TbTypeTemplate> findAll() {
        return tbTypeTemplateMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize,TbTypeTemplate tbTypeTemplate) {
        PageHelper.startPage(pageNum, pageSize);
        TbTypeTemplateExample tbTypeTemplateExample=new TbTypeTemplateExample();
        TbTypeTemplateExample.Criteria criteria = tbTypeTemplateExample.createCriteria();
        if (ObjectUtil.isNotEmpty(tbTypeTemplate)){
            if (ObjectUtil.isNotEmpty(tbTypeTemplate.getName())){
                criteria.andNameLike("%"+tbTypeTemplate.getName()+"%");
            }
        }
        Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) tbTypeTemplateMapper.selectByExample(tbTypeTemplateExample);
        saveToRedis();
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbTypeTemplate tbTypeTemplate) {
        tbTypeTemplateMapper.insert(tbTypeTemplate);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbTypeTemplate tbTypeTemplate) {
        tbTypeTemplateMapper.updateByPrimaryKey(tbTypeTemplate);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbTypeTemplate findOne(Long id) {
        return tbTypeTemplateMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for(Long id : ids) {
            tbTypeTemplateMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public List<Map> findSpecList(long id) {
        TbTypeTemplate tbTypeTemplate = tbTypeTemplateMapper.selectByPrimaryKey(id);
        List<Map> objects = JSON.parseArray(tbTypeTemplate.getSpecIds(), Map.class);
        objects.forEach(map -> {
            TbSpecificationOptionExample optionExample = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = optionExample.createCriteria();
            criteria.andSpecIdEqualTo(new Long((Integer) map.get("id")));
            List<TbSpecificationOption> tbSpecificationOptions = tbSpecificationOptionMapper.selectByExample(optionExample);
            map.put("options", tbSpecificationOptions);
        });
        return objects;
    }

    private void saveToRedis() {
        //品牌列表缓存
        //查询所有的模板
        List<TbTypeTemplate> tbTypeTemplates = tbTypeTemplateMapper.selectByExample(null);
        tbTypeTemplates.forEach(typeTemplate -> {
            //查询每一个模板id对应的品牌列表
            redisTemplate.boundHashOps(BRAND_LIST_REDIS_NAME).put(typeTemplate.getId(), JSON.parseArray(typeTemplate.getBrandIds(), Map.class));
            //查询每一个模板id对应的规格列表
            redisTemplate.boundHashOps(SPEC_LIST_REDIS_NAME).put(typeTemplate.getId(), findSpecList(typeTemplate.getId()));
        });
    }
}
