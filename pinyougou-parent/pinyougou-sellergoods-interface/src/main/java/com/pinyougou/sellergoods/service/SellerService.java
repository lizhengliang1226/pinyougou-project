package com.pinyougou.sellergoods.service;
import java.util.List;

import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbSeller;

/**
 * 品牌服务层接口
 * @author Administrator
 *
 */
public interface SellerService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSeller> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbSeller tbSeller);
	
	
	/**
	 * 修改
	 */
	public void update(TbSeller tbSeller);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbSeller findOne(String id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(String [] ids);

	/**
	 * 分页条件查询
	 * @param tbSeller
	 * @param page
	 * @param size
	 * @return
	 */
	public PageResult findPage(TbSeller tbSeller, int page, int size);
	public void updateStatus(String sellerId,String status);
}
