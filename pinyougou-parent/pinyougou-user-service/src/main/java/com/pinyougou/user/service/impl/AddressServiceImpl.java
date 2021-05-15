package com.pinyougou.user.service.impl;
import java.util.List;

import com.github.pagehelper.Page;
import com.pinyougou.mapper.TbAddressMapper;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbAddress;
import com.pinyougou.pojo.TbAddressExample;
import com.pinyougou.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;


/**
 * 品牌业务逻辑层
 * @author Administrator
 *
 */
@Service
public class AddressServiceImpl implements AddressService {


	@Autowired
	private TbAddressMapper tb_addressMapper;

	@Override
	public List<TbAddress> findListByUserId(String userId) {
		TbAddressExample addressExample=new TbAddressExample();
		TbAddressExample.Criteria criteria = addressExample.createCriteria();
		criteria.andUserIdEqualTo(userId);
		addressExample.setOrderByClause("is_default");
		List<TbAddress> tbAddresses = tb_addressMapper.selectByExample(addressExample);
		return tbAddresses;
	}
	/**
	 * 查询全部
	 */
	@Override
	public List<TbAddress> findAll() {
		return tb_addressMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbAddress> page=   (Page<TbAddress>) tb_addressMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbAddress tb_address) {
		tb_addressMapper.insert(tb_address);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbAddress tb_address){
		tb_addressMapper.updateByPrimaryKey(tb_address);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbAddress findOne(Long id){
		return tb_addressMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			tb_addressMapper.deleteByPrimaryKey(id);
		}		
	}
	
}
