package com.pinyougou.cart.service.policy;

import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbOrderItem;
import com.pinyougou.pojogroup.Cart;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.12-19:43
 */
public class MerchantNotExist implements AddMerchandisePolicy{
    @Override
    public void addPolicy(Cart cart, List<Cart> cartList, Integer num, TbItem tbItem) {
        List<TbOrderItem> list = new ArrayList<>();
        TbOrderItem orderItem = AddMerchandisePolicy.setOrderItem(num, tbItem);
        list.add(orderItem);
        cart = new Cart(tbItem.getSellerId(), tbItem.getSeller(), list);
        cartList.add(cart);
    }
}
