package com.pinyougou.sellergoods.service;
import java.util.List;

import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojogroup.Goods;

/**
 * 品牌服务层接口
 * @author Administrator
 *
 */
public interface GoodsService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbGoods> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(Goods goods);
	
	
	/**
	 * 修改
	 */
	public void update(Goods tbGoods);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public Goods findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(TbGoods tbGoods,int pageNum, int pageSize);

	/**
	 * 批量更新状态
	 * @param ids
	 */
	public void updateStatus(Long[] ids,String status);
	/**
	 * 批量更新上下架状态
	 * @param ids
	 */
	public void updateMarkStatus(Long[] ids,String status);
}
