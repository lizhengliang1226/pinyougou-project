package com.pinyougou.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.mapper.TbTypeTemplateMapper;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.sellergoods.service.TypeTemplateService;
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
public class TypeTemplateServiceImpl implements TypeTemplateService {

	@Autowired
	private TbTypeTemplateMapper tbTypeTemplateMapper;
	@Autowired
	private TbSpecificationOptionMapper tbSpecificationOptionMapper;
	/**
	 * 查询全部
	 */
	@Override
	public List<TbTypeTemplate> findAll() {
		return tbTypeTemplateMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbTypeTemplate> page=   (Page<TbTypeTemplate>) tbTypeTemplateMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbTypeTemplate tbTypeTemplate) {
		tbTypeTemplateMapper.insert(tbTypeTemplate);
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbTypeTemplate tbTypeTemplate){
		tbTypeTemplateMapper.updateByPrimaryKey(tbTypeTemplate);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbTypeTemplate findOne(Long id){
		return tbTypeTemplateMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			tbTypeTemplateMapper.deleteByPrimaryKey(id);
		}		
	}

	@Override
	public List<Map> findSpecList(long id) {
		TbTypeTemplate tbTypeTemplate = tbTypeTemplateMapper.selectByPrimaryKey(id);
		List<Map> objects = JSON.parseArray(tbTypeTemplate.getSpecIds(),Map.class);
		objects.forEach(map->{
			TbSpecificationOptionExample optionExample = new TbSpecificationOptionExample();
			TbSpecificationOptionExample.Criteria criteria = optionExample.createCriteria();
			criteria.andSpecIdEqualTo(new Long((Integer) map.get("id")));
			List<TbSpecificationOption> tbSpecificationOptions = tbSpecificationOptionMapper.selectByExample(optionExample);
			map.put("options",tbSpecificationOptions);
		});
		return objects;
	}

}
