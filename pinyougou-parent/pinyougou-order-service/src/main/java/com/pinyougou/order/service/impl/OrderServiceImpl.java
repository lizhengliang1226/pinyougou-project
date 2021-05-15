package com.pinyougou.order.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbOrderItemMapper;
import com.pinyougou.mapper.TbOrderMapper;
import com.pinyougou.mapper.TbPayLogMapper;
import com.pinyougou.order.service.OrderService;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbOrder;
import com.pinyougou.pojo.TbOrderExample;
import com.pinyougou.pojo.TbPayLog;
import com.pinyougou.pojogroup.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


/**
 * 品牌业务逻辑层
 * @author Administrator
 *
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	private TbPayLogMapper payLogMapper;
	@Autowired
	private TbOrderMapper tb_orderMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbOrder> findAll() {
		return tb_orderMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbOrder> page=   (Page<TbOrder>) tb_orderMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private IdWorker idWorker;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Override
	public void add(TbOrder tb_order) {
		//提取购物车的数据，循环生成订单和明细
		List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartList").get(tb_order.getUserId());
		if (ObjectUtil.isEmpty(cartList)){
			return;
		}
		//支付总金额
		AtomicReference<Double> totalFee= new AtomicReference<>((double) 0);
		//支付订单号
		String outTradeNo = String.valueOf(idWorker.nextId());
		cartList.forEach(cart -> {
			//创建订单，插入的是tb_order
			TbOrder tbOrder=new TbOrder() ;
			tbOrder.setCreateTime(new Date());//创建时间
			tbOrder.setSellerId(cart.getSellerId());//商家id
			tbOrder.setUserId(tb_order.getUserId());//用户id
			tbOrder.setPaymentType(tb_order.getPaymentType());//支付类型
			tbOrder.setReceiverAreaName(tb_order.getReceiverAreaName());//收件人姓名
			tbOrder.setReceiverMobile(tb_order.getReceiverMobile());//收件人手机号
			tbOrder.setReceiver(tb_order.getReceiver());//收件人
			tbOrder.setOrderId(idWorker.nextId());//订单id
			tbOrder.setStatus("1");//支付状态，1未支付
			tbOrder.setSourceType(tb_order.getSourceType());//订单来源：app，手机，微信
			tbOrder.setOutTradeNo(outTradeNo);//订单号
			AtomicReference<Double> money= new AtomicReference<>((double) 0);
			cart.getOrderItemList().forEach(orderItem->{
				long id = idWorker.nextId();
				orderItem.setId(id);
				orderItem.setOrderId(tbOrder.getOrderId());
				orderItemMapper.insert(orderItem);
				money.updateAndGet(v -> (double) (v + orderItem.getTotalFee().doubleValue()));
			});
			totalFee.updateAndGet(v -> (double) (v + money.get()));
			//实付金额
			tbOrder.setPayment(BigDecimal.valueOf(money.get()));
			tb_orderMapper.insert(tbOrder);
		});
		if ("1".equals(tb_order.getPaymentType())){
			TbPayLog payLog=new TbPayLog();
			payLog.setOutTradeNo(outTradeNo);//支付订单号
			payLog.setCreateTime(new Date());
			payLog.setPayType("1");//支付类型
			payLog.setTotalFee((long)(totalFee.get()*100));
			payLog.setTradeState("0");//支付状态
			payLog.setUserId(tb_order.getUserId());
			payLogMapper.insert(payLog);
			redisTemplate.boundHashOps("payLog").put(tb_order.getUserId(),payLog);
		}
		redisTemplate.boundHashOps("cartList").delete(tb_order.getUserId());
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbOrder tb_order){
		tb_orderMapper.updateByPrimaryKey(tb_order);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbOrder findOne(Long id){
		return tb_orderMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			tb_orderMapper.deleteByPrimaryKey(id);
		}		
	}

	@Override
	public TbPayLog searchPayLogFromRedis(String userId) {
		return (TbPayLog) redisTemplate.boundHashOps("payLog").get(userId);
	}

	@Override
	public void updateOrderStatus(String out_trade_no, String transaction_id) {
		//修改payLog状态为1
		TbPayLog payLog = payLogMapper.selectByPrimaryKey(out_trade_no);
		payLog.setPayTime(new Date());
		payLog.setTradeState("1");
		payLog.setTransactionId(transaction_id);
		payLogMapper.updateByPrimaryKey(payLog);
		//修改订单状态为2
		TbOrderExample tbOrderExample = new TbOrderExample();
		TbOrderExample.Criteria criteria = tbOrderExample.createCriteria();
		criteria.andOutTradeNoEqualTo(out_trade_no);
		List<TbOrder> tbOrders = tb_orderMapper.selectByExample(tbOrderExample);
		tbOrders.forEach(order->{
			order.setPaymentTime(new Date());
			order.setStatus("2");
			tb_orderMapper.updateByPrimaryKey(order);
		});
		//清除缓存
		redisTemplate.boundHashOps("payLog").delete(payLog.getUserId());
	}

}
