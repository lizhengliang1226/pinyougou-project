package com.pinyougou.cart.service.policy;

import cn.hutool.core.util.ObjectUtil;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbOrderItem;
import com.pinyougou.pojogroup.Cart;


import java.math.BigDecimal;
import java.util.List;

/**
 * @Description 商家存在的策略
 * @Author LZL
 * @Date 2021.05.12-19:49
 */
public class MerchantExist implements AddMerchandisePolicy {
    private static TbOrderItem orderItem;
    private final PolicyFactory policyFactory=PolicyFactory.getInstance();
    @Override
    public void addPolicy(Cart cart, List<Cart> cartList, Integer num, TbItem tbItem) {
        if (orderItemIsExist(cart, tbItem)) {
            MerchandiseExist merchandiseExist = (MerchandiseExist) policyFactory
                    .getGoodsPolicy(Policy.MERCHANDISE_EXIST);
            merchandiseExist.addPolicy(cart, cartList, num, tbItem);
        } else {
            MerchandiseNotExist merchandiseNotExist = (MerchandiseNotExist) policyFactory
                    .getGoodsPolicy(Policy.MERCHANDISE_NOT_EXIST);
            merchandiseNotExist.addPolicy(cart, cartList, num, tbItem);
        }
    }

    //商品不存在策略
    static class MerchandiseNotExist implements MerchandisePolicy {
        @Override
        public void addPolicy(Cart cart, List<Cart> cartList, Integer num, TbItem tbItem) {
            orderItem =AddMerchandisePolicy.setOrderItem(num, tbItem);
            cart.getOrderItemList().add(orderItem);
        }
    }

    //商品存在策略
    static class MerchandiseExist implements MerchandisePolicy {
        @Override
        public void addPolicy(Cart cart, List<Cart> cartList, Integer num, TbItem tbItem) {
            orderItem.setNum(orderItem.getNum() + num);
            if (orderItem.getNum() <= 0) {
                cart.getOrderItemList().remove(orderItem);
            }
            if (ObjectUtil.isEmpty(cart.getOrderItemList())) {
                cartList.remove(cart);
            }
            orderItem.setTotalFee(BigDecimal.valueOf(orderItem.getPrice().doubleValue() * orderItem.getNum()));
        }
    }

    //判断商品是否存在
    private boolean orderItemIsExist(Cart cart, TbItem tbItem) {
        for(TbOrderItem orderItem : cart.getOrderItemList()) {
            if (orderItem.getItemId().longValue() == tbItem.getId().longValue()) {
                MerchantExist.orderItem = orderItem;
                return true;
            }
        }
        return false;
    }
}

interface MerchandisePolicy {
    void addPolicy(Cart cart, List<Cart> cartList, Integer num, TbItem tbItem);
}