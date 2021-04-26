package com.pinyougou.content.service.impl;
import java.util.List;

import com.pinyougou.content.service.ContentCategoryService;
import com.pinyougou.mapper.TbContentCategoryMapper;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


/**
 * 品牌业务逻辑层
 * @author Administrator
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper tb_content_categoryMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbContentCategory> findAll() {
		return tb_content_categoryMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbContentCategory> page=   (Page<TbContentCategory>) tb_content_categoryMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbContentCategory tb_content_category) {
		tb_content_categoryMapper.insert(tb_content_category);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbContentCategory tb_content_category){
		tb_content_categoryMapper.updateByPrimaryKey(tb_content_category);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbContentCategory findOne(Long id){
		return tb_content_categoryMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			tb_content_categoryMapper.deleteByPrimaryKey(id);
		}		
	}
	
}
