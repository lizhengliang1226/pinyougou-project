package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.pageentity.LoginResult;
import com.pinyougou.pojogroup.Cart;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.12-21:27
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Reference
    private CartService cartService;

    @RequestMapping("/addGoodsToCartList")
    public LoginResult addGoodsToCartList(@RequestBody List<Cart> cartList, Long itemId, Integer num) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if (name.equals("anonymousUser")) {
            try {
                List<Cart> carts = cartService.addGoodsToCartList(cartList, itemId, num);
                return new LoginResult(true, "", carts);
            } catch (Exception e) {
                e.printStackTrace();
                return new LoginResult(false, "", "添加失败");
            }
        } else {
            List<Cart> cartListFromRedis = cartService.findCartListFromRedis(name);
            cartListFromRedis = cartService.addGoodsToCartList(cartListFromRedis, itemId, num);
            cartService.saveCartListToRedis(name, cartListFromRedis);
            return new LoginResult(true, name, cartListFromRedis);
        }

    }

    @RequestMapping("/findCartList")
    public LoginResult findCartList(@RequestBody List<Cart> cartList) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if (name.equals("anonymousUser")) {
            return new LoginResult(true, "", cartList);
        } else {
            List<Cart> cartListFromRedis = cartService.findCartListFromRedis(name);
            if (cartList.size()>0){
                cartListFromRedis = cartService.mergeCartList(cartListFromRedis, cartList);
                cartService.saveCartListToRedis(name, cartListFromRedis);
            }
            return new LoginResult(true, name, cartListFromRedis);
        }
    }
}
