package com.pinyougou.sellergoods.service.impl;
import java.util.List;

import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.sellergoods.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;


/**
 * 品牌业务逻辑层
 * @author Administrator
 *
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbItem> findAll() {
		return tbItemMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbItem> page=   (Page<TbItem>) tbItemMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbItem tbItem) {
		tbItemMapper.insert(tbItem);
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbItem tbItem){
		tbItemMapper.updateByPrimaryKey(tbItem);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbItem findOne(Long id){
		return tbItemMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			tbItemMapper.deleteByPrimaryKey(id);
		}		
	}
	
}
