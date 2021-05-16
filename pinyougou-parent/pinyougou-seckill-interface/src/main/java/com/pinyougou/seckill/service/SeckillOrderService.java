package com.pinyougou.seckill.service;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbSeckillOrder;

import java.util.List;

/**
 * 品牌服务层接口
 * @author Administrator
 *
 */
public interface SeckillOrderService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSeckillOrder> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbSeckillOrder tb_seckill_order);
	
	
	/**
	 * 修改
	 */
	public void update(TbSeckillOrder tb_seckill_order);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbSeckillOrder findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 秒杀提交订单
	 * @param seckillId
	 * @param userId
	 */
	public void submit(Long seckillId,String userId);

	/**
	 * 查询秒杀订单从缓存中通过userid
	 * @param userId
	 * @return
	 */
	public TbSeckillOrder searchSecKillOrderFromRedisByUserId(String userId);

	/**
	 * 从缓存保存订单到数据库
	 * @param userId 用户id，用于从缓存中查询订单
	 * @param orderId 订单id
	 * @param transaction_id 微信交易流水号
	 */
	public void saveOrderToDb(String userId,Long orderId,String transaction_id );

	/**
	 * 从缓存中删除订单
	 * @param userId 用户id
	 * @param orderId 订单id
	 */
	public void deleteOrderFromRedis(String userId,Long orderId);
}
