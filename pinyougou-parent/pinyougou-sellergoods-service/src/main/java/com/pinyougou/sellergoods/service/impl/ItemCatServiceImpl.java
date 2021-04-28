package com.pinyougou.sellergoods.service.impl;
import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.pojo.TbItemCatExample;
import com.pinyougou.sellergoods.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;


/**
 * 品牌业务逻辑层
 * @author Administrator
 *
 */
@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Autowired
	private RedisTemplate redisTemplate;

	@Value("${ITEM_CAT_REDIS_NAME}")
	private String ITEM_CAT_REDIS_NAME;
	/**
	 * 查询全部
	 */
	@Override
	public List<TbItemCat> findAll() {
		return tbItemCatMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbItemCat> page=   (Page<TbItemCat>) tbItemCatMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbItemCat tbItemCat) {
		tbItemCatMapper.insert(tbItemCat);
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbItemCat tbItemCat){
		tbItemCatMapper.updateByPrimaryKey(tbItemCat);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbItemCat findOne(Long id){
		return tbItemCatMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		//判断每一个id下是否有下级分类
		for(Long id:ids){
			List<TbItemCat> byParentId = findByParentId(id);
			if (!ObjectUtil.isEmpty(byParentId)) {
				throw new RuntimeException("不能删除有下级分类的分类");
			}
			tbItemCatMapper.deleteByPrimaryKey(id);
		}

	}

	@Override
	public List<TbItemCat> findByParentId(Long id) {
		TbItemCatExample tbItemCatExample = new TbItemCatExample();
		TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
		criteria.andParentIdEqualTo(id);
		List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(null);
		tbItemCats.forEach(itemCat->{
			redisTemplate.boundHashOps(ITEM_CAT_REDIS_NAME).put(itemCat.getName(),itemCat.getTypeId());
		});
		return tbItemCatMapper.selectByExample(tbItemCatExample);
	}

}
