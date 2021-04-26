package com.pinyougou.sellergoods.service.impl;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.pinyougou.mapper.TbSellerMapper;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.pojo.TbSellerExample;
import com.pinyougou.sellergoods.service.SellerService;
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
public class SellerServiceImpl implements SellerService {

	@Autowired
	private TbSellerMapper tbSellerMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSeller> findAll() {
		return tbSellerMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSeller> page=   (Page<TbSeller>) tbSellerMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbSeller tb_seller) {
		tb_seller.setStatus("0");
		tb_seller.setCreateTime(new Date());
		tbSellerMapper.insert(tb_seller);
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbSeller tb_seller){
		tbSellerMapper.updateByPrimaryKey(tb_seller);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbSeller findOne(String id){
		return tbSellerMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		for(String id:ids){
			tbSellerMapper.deleteByPrimaryKey(id);
		}		
	}

	@Override
	public PageResult findPage(TbSeller tbSeller, int page, int size) {
		PageHelper.startPage(page,size);
		TbSellerExample sellerExample=new TbSellerExample();
		TbSellerExample.Criteria criteria = sellerExample.createCriteria();
		criteria.andStatusEqualTo(tbSeller.getStatus());
		Page<TbSeller> page1 = (Page<TbSeller>) tbSellerMapper.selectByExample(sellerExample);
		return new PageResult(page1.getTotal(), page1.getResult());
	}

	@Override
	public void updateStatus(String sellerId, String status) {
		TbSeller tbSeller = tbSellerMapper.selectByPrimaryKey(sellerId);
		System.out.println(sellerId);
		tbSeller.setStatus(status);
		tbSellerMapper.updateByPrimaryKey(tbSeller);
	}

}
