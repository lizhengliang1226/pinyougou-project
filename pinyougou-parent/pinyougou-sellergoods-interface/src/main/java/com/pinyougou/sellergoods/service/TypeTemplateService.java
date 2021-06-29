package com.pinyougou.sellergoods.service;

import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbTypeTemplate;

import java.util.List;
import java.util.Map;

/**
 * 品牌服务层接口
 * @author Administrator
 *
 */
public interface TypeTemplateService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbTypeTemplate> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize,TbTypeTemplate tbTypeTemplate);
	
	
	/**
	 * 增加
	*/
	public void add(TbTypeTemplate tbTypeTemplate);
	
	
	/**
	 * 修改
	 */
	public void update(TbTypeTemplate tbTypeTemplate);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbTypeTemplate findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 根据模板id查询规格列表
	 * @param id
	 * @return
	 */
	public List<Map> findSpecList(long id);
}
