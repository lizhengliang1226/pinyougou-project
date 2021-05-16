package com.pinyougou.seckill.service.impl;
import java.util.Date;
import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.pojo.TbSeckillGoodsExample;
import com.pinyougou.seckill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * 品牌业务逻辑层
 * @author Administrator
 *
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

	@Autowired
	private TbSeckillGoodsMapper tb_seckill_goodsMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSeckillGoods> findAll() {
		return tb_seckill_goodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSeckillGoods> page=   (Page<TbSeckillGoods>) tb_seckill_goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbSeckillGoods tb_seckill_goods) {
		tb_seckill_goodsMapper.insert(tb_seckill_goods);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbSeckillGoods tb_seckill_goods){
		tb_seckill_goodsMapper.updateByPrimaryKey(tb_seckill_goods);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbSeckillGoods findOne(Long id){
		return tb_seckill_goodsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			tb_seckill_goodsMapper.deleteByPrimaryKey(id);
		}		
	}
	@Autowired
	private RedisTemplate redisTemplate;
	@Override
	public List<TbSeckillGoods> findList() {
		List<TbSeckillGoods> seckillGoodsList = redisTemplate.boundHashOps("seckillGoods").values();
		if (ObjectUtil.isEmpty(seckillGoodsList)){
			TbSeckillGoodsExample tbSeckillGoodsExample = new TbSeckillGoodsExample();
			TbSeckillGoodsExample.Criteria criteria = tbSeckillGoodsExample.createCriteria();
			criteria
					.andStartTimeLessThanOrEqualTo(new Date())
					.andStatusEqualTo("1")
					.andStockCountGreaterThan(0)
					.andEndTimeGreaterThan(new Date());
			seckillGoodsList = tb_seckill_goodsMapper.selectByExample(tbSeckillGoodsExample);
			seckillGoodsList.forEach(seckillGoods->{
				redisTemplate.boundHashOps("seckillGoods").put(seckillGoods.getId(),seckillGoods);
			});
		}
		return seckillGoodsList;
	}

	@Override
	public TbSeckillGoods findOneFromRedis(Long id) {
		return (TbSeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(id);
	}

}
