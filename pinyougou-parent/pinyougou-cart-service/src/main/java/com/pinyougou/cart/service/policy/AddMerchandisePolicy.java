package com.pinyougou.cart.service.policy;

import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbOrderItem;
import com.pinyougou.pojogroup.Cart;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description 添加商品策略
 * @Author LZL
 * @Date 2021.05.12-19:39
 */
public interface AddMerchandisePolicy {
    void addPolicy(Cart cart, List<Cart> cartList, Integer num, TbItem tbItem);
     static TbOrderItem setOrderItem(Integer num, TbItem tbItem) {
        if (num<=0){
            throw new RuntimeException("购物车商品数量不合法！");
        }
        TbOrderItem orderItem=new TbOrderItem();
        orderItem.setItemId(tbItem.getId());
        orderItem.setGoodsId(tbItem.getGoodsId());
        orderItem.setPicPath(tbItem.getImage());
        orderItem.setNum(num);
        orderItem.setPrice(tbItem.getPrice());
        orderItem.setSellerId(tbItem.getSellerId());
        orderItem.setTitle(tbItem.getTitle());
        orderItem.setTotalFee(new BigDecimal(num * tbItem.getPrice().doubleValue()));
        return orderItem;
    }
}
