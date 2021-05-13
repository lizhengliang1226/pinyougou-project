package com.pinyougou.cart.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.cart.service.policy.AddMerchandisePolicy;
import com.pinyougou.cart.service.policy.PolicyFactory;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbOrderItem;
import com.pinyougou.pojogroup.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.10-21:03
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 根据skuId查询商品信息
     * 获取商家ID
     * 查询购物车列表中是否存在该商家
     * 不存在，则构建购物车对象中的明细列表添加明细
     * 存在，则判断商品是否在购物车明细列表中存在
     * - 存在，则数量+1，金额更新
     * - 不存在，创建明细
     *
     * @param cartList 购物车
     * @param itemId   skuId
     * @param num      商品数量
     * @return 购物车列表
     */
    @Override
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {
        TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
        if (ObjectUtil.isNull(tbItem) || !tbItem.getStatus().equals("1")) {
            throw new RuntimeException("查询不到商品或者商品状态为不可用");
        }
        String sellerId = tbItem.getSellerId();
        Cart cart = getCart(cartList, sellerId);
        PolicyFactory policyFactory=PolicyFactory.getInstance();
        AddMerchandisePolicy policy = policyFactory.getPolicy(cart);
        policy.addPolicy(cart,cartList,num,tbItem);
        return cartList;
    }

    /**
     * 根据商家id查询购物车列表中是否存在商家，存在返回cart
     *
     * @param cartList 购物车列表
     * @param sellerId 商家id
     * @return cart对象
     */
    private Cart getCart(List<Cart> cartList, String sellerId) {
        for(Cart cart : cartList) {
            if (cart.getSellerId().equals(sellerId)) {
                return cart;
            }
        }
        return null;
    }

    @Override
    public List<Cart> findCartListFromRedis(String username) {
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartList").get(username);
        return ObjectUtil.isEmpty(cartList)?new ArrayList<>(): cartList;
    }

    @Override
    public void saveCartListToRedis(String username, List<Cart> cartList) {
        redisTemplate.boundHashOps("cartList").put(username,cartList);
    }

    @Override
    public List<Cart> mergeCartList(List<Cart> cartList1, List<Cart> cartList2) {
        for(Cart cart : cartList1) {
            for(TbOrderItem orderItem : cart.getOrderItemList()) {
                cartList2=addGoodsToCartList(cartList2,orderItem.getItemId(),orderItem.getNum());
            }
        }
        return cartList2;
    }
}
