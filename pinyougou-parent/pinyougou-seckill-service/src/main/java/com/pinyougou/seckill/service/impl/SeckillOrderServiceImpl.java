package com.pinyougou.seckill.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.mapper.TbSeckillOrderMapper;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.pojo.TbSeckillOrder;
import com.pinyougou.seckill.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import util.IdWorker;

import java.util.Date;
import java.util.List;


/**
 * 品牌业务逻辑层
 *
 * @author Administrator
 */
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private TbSeckillOrderMapper tb_seckill_orderMapper;
    @Autowired
    private TbSeckillGoodsMapper goodsMapper;
    /**
     * 查询全部
     */
    @Override
    public List<TbSeckillOrder> findAll() {
        return tb_seckill_orderMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbSeckillOrder> page = (Page<TbSeckillOrder>) tb_seckill_orderMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbSeckillOrder tb_seckill_order) {
        tb_seckill_orderMapper.insert(tb_seckill_order);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbSeckillOrder tb_seckill_order) {
        tb_seckill_orderMapper.updateByPrimaryKey(tb_seckill_order);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbSeckillOrder findOne(Long id) {
        return tb_seckill_orderMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for(Long id : ids) {
            tb_seckill_orderMapper.deleteByPrimaryKey(id);
        }
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Override
    public void submit(Long seckillId, String userId) {
        TbSeckillGoods o = (TbSeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(seckillId);
        if (o == null) {
            throw new RuntimeException("缓存中没有数据");
        }
        if (o.getStockCount() <= 0) {
            throw new RuntimeException("库存为空");
        }
        if (o.getEndTime().getTime() <= new Date().getTime()) {
            throw new RuntimeException("秒杀时间已过期");
        }
        o.setStockCount(o.getStockCount() - 1);
        redisTemplate.boundHashOps("seckillGoods").put(seckillId, o);
        if (o.getStockCount() == 0) {
            goodsMapper.updateByPrimaryKey(o);
            redisTemplate.boundHashOps("seckillGoods").delete(seckillId);
        }
        TbSeckillOrder order = new TbSeckillOrder();
        order.setId(idWorker.nextId());
        order.setCreateTime(new Date());
        order.setMoney(o.getCostPrice());
        order.setSeckillId(seckillId);
        order.setSellerId(o.getSellerId());
        order.setStatus("0");
        order.setUserId(userId);
        redisTemplate.boundHashOps("seckillOrder").put(userId, order);
    }

    @Override
    public TbSeckillOrder searchSecKillOrderFromRedisByUserId(String userId) {
        TbSeckillOrder seckillOrder = (TbSeckillOrder) redisTemplate.boundHashOps("seckillOrder").get(userId);
        return seckillOrder;
    }

    @Override
    public void saveOrderToDb(String userId, Long orderId, String transaction_id) {
        //从缓存中查询订单
        TbSeckillOrder seckillOrder = (TbSeckillOrder) redisTemplate.boundHashOps("seckillOrder").get(userId);
        //验证订单有效性
        if (seckillOrder == null) {
            throw new RuntimeException("订单在缓存中不存在");
        }
        if (seckillOrder.getId().longValue() != orderId.longValue()) {
            throw new RuntimeException("订单号不相同");
        }
        //设置支付相关信息
        seckillOrder.setPayTime(new Date());
        seckillOrder.setStatus("1");
        seckillOrder.setTransactionId(transaction_id);
        //插入数据库
        tb_seckill_orderMapper.insert(seckillOrder);
        //删除缓存中的数据
        redisTemplate.boundHashOps("seckillOrder").delete(userId);
    }

    @Override
    public void deleteOrderFromRedis(String userId, Long orderId) {
        TbSeckillOrder order = (TbSeckillOrder) redisTemplate.boundHashOps("seckillOrder").get(userId);
        if (order != null) {
            redisTemplate.boundHashOps("seckillOrder").delete(userId);
            TbSeckillGoods goods = (TbSeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(order.getSeckillId());
            if (goods != null) {
                goods.setStockCount(goods.getStockCount() + 1);
                redisTemplate.boundHashOps("seckillGoods").put(goods.getId(),goods);
            }else{
                TbSeckillGoods tbSeckillGoods = goodsMapper.selectByPrimaryKey(order.getSeckillId());
                if (tbSeckillGoods.getEndTime().getTime()>new Date().getTime()) {
                    tbSeckillGoods.setStockCount(1);
                    redisTemplate.boundHashOps("seckillGoods").put(order.getSeckillId(),tbSeckillGoods);
                }
            }

        }

    }
}
