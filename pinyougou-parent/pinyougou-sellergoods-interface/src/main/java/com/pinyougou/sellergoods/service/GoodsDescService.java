package com.pinyougou.sellergoods.service;
import java.util.List;

import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbGoodsDesc;



/**
 * 品牌服务层接口
 * @author Administrator
 *
 */
public interface GoodsDescService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbGoodsDesc> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbGoodsDesc tbGoodsDesc);
	
	
	/**
	 * 修改
	 */
	public void update(TbGoodsDesc tbGoodsDesc);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbGoodsDesc findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

}
