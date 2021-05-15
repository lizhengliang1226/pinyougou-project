package com.pinyougou.user.service;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbAddress;

import java.util.List;

/**
 * 品牌服务层接口
 * @author Administrator
 *
 */
public interface AddressService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbAddress> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbAddress tb_address);
	
	
	/**
	 * 修改
	 */
	public void update(TbAddress tb_address);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbAddress findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 查找地址清单通过userId
	 * @param userId
	 * @return
	 */
	public List<TbAddress> findListByUserId(String userId);
}
