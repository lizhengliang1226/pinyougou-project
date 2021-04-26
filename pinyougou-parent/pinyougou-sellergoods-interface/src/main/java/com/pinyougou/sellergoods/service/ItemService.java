package com.pinyougou.sellergoods.service;
import java.util.List;

import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbItem;

/**
 * 品牌服务层接口
 * @author Administrator
 *
 */
public interface ItemService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbItem> findAll();


	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);


	/**
	 * 增加
	 */
	public void add(TbItem tbItem);


	/**
	 * 修改
	 */
	public void update(TbItem tbItem);


	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbItem findOne(Long id);


	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

}
