package com.pinyougou.order.service;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbOrder;
import com.pinyougou.pojo.TbPayLog;

import java.util.List;

/**
 * 品牌服务层接口
 * @author Administrator
 *
 */
public interface OrderService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbOrder> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbOrder tb_order);
	
	
	/**
	 * 修改
	 */
	public void update(TbOrder tb_order);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbOrder findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 查询支付日志从缓存
	 * @param userId
	 * @return
	 */
	public TbPayLog searchPayLogFromRedis(String userId);

	/**
	 * 更新订单状态为已支付
	 * @param out_trade_no 支付订单号
	 * @param transaction_id 交易流水号
	 */
	public void updateOrderStatus(String out_trade_no,String transaction_id);
}
