package com.pinyougou.sellergoods.service;

import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbItemCat;

import java.util.List;

/**
 * 品牌服务层接口
 * @author Administrator
 *
 */
public interface ItemCatService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbItemCat> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbItemCat tbItemCat);
	
	
	/**
	 * 修改
	 */
	public void update(TbItemCat tbItemCat);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbItemCat findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 通过parentId查找父级
	 * @param id
	 * @return
	 */
	public List<TbItemCat> findByParentId(Long id);

}
