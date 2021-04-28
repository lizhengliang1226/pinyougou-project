package com.pinyougou.content.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.pinyougou.content.service.ContentService;
import com.pinyougou.mapper.TbContentMapper;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbContent;
import com.pinyougou.pojo.TbContentExample;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 品牌业务逻辑层
 *
 * @author Administrator
 */
@Service
@Transactional
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tb_contentMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部
     */
    @Override
    public List<TbContent> findAll() {
        return tb_contentMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbContent> page = (Page<TbContent>) tb_contentMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbContent tb_content) {
        tb_contentMapper.insert(tb_content);
        //清空缓存
        redisTemplate.boundHashOps("contentList").delete(tb_content.getCategoryId());
    }


    /**
     * 修改
     */
    @Override
    public void update(TbContent tb_content) {
        Long categoryId = tb_contentMapper.selectByPrimaryKey(tb_content.getId()).getCategoryId();
        //清空源分组缓存
        redisTemplate.boundHashOps("contentList").delete(categoryId);
        tb_contentMapper.updateByPrimaryKey(tb_content);
        if (categoryId.longValue() != tb_content.getCategoryId().longValue()) {
            //清空现在分组缓存
            redisTemplate.boundHashOps("contentList").delete(tb_content.getCategoryId());
        }
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbContent findOne(Long id) {
        return tb_contentMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for(Long id : ids) {
            TbContent tbContent = tb_contentMapper.selectByPrimaryKey(id);
            //清空缓存
            redisTemplate.boundHashOps("contentList").delete(tbContent.getCategoryId());
            tb_contentMapper.deleteByPrimaryKey(id);
        }

    }

    @Override
    public List<TbContent> findByCategoryId(Long categoryId) {
        List<TbContent> list = (List<TbContent>) redisTemplate.boundHashOps("contentList").get(categoryId);
        if (ObjectUtil.isEmpty(list)) {
            TbContentExample tbContentExample = new TbContentExample();
            TbContentExample.Criteria criteria = tbContentExample.createCriteria();
            criteria.andCategoryIdEqualTo(categoryId);
            criteria.andStatusEqualTo("1");
            tbContentExample.setOrderByClause("sort_order");//排序
            List<TbContent> tbContents = tb_contentMapper.selectByExample(tbContentExample);
            redisTemplate.boundHashOps("contentList").put(categoryId, tbContents);
            return tbContents;
        }
        return list;
    }

}
