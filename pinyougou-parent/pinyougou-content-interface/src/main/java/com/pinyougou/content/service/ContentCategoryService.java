package com.pinyougou.content.service;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbContentCategory;

import java.util.List;

/**
 * 品牌服务层接口
 * @author Administrator
 *
 */
public interface ContentCategoryService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbContentCategory> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbContentCategory tb_content_category);
	
	
	/**
	 * 修改
	 */
	public void update(TbContentCategory tb_content_category);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbContentCategory findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

}
