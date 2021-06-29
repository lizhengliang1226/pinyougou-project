package com.pinyougou.sellergoods.service;
import java.util.List;
import java.util.Map;

import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojogroup.Specification;

/**
 * 品牌服务层接口
 * @author Administrator
 *
 */
public interface SpecificationService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSpecification> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize,TbSpecification tbSpecification);
	
	
	/**
	 * 增加
	*/
	public void add(Specification tbSpecification);
	
	
	/**
	 * 修改
	 */
	public void update(Specification tbSpecification);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public Specification findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);
	public List<Map> selectSpecificationList();

}
