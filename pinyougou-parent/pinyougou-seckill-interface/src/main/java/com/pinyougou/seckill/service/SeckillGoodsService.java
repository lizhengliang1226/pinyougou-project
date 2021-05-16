package com.pinyougou.seckill.service;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbSeckillGoods;

import java.util.List;

/**
 * 品牌服务层接口
 * @author Administrator
 *
 */
public interface SeckillGoodsService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSeckillGoods> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbSeckillGoods tb_seckill_goods);
	
	
	/**
	 * 修改
	 */
	public void update(TbSeckillGoods tb_seckill_goods);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbSeckillGoods findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 查询正在参与秒杀的商品
	 * @return
	 */
	public List<TbSeckillGoods> findList();

	/**
	 * 根据id从缓存中查询商品
	 * @param id
	 * @return
	 */
	public TbSeckillGoods findOneFromRedis(Long id);
}
