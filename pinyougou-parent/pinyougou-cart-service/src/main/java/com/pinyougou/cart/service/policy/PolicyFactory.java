package com.pinyougou.cart.service.policy;

import com.pinyougou.pojogroup.Cart;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 策略工厂
 * @Author LZL
 * @Date 2021.05.12-20:20
 */
public class PolicyFactory {
    private static final Map<Policy, Object> policyMap = new HashMap<>();

    private PolicyFactory() {
    }

    private static final PolicyFactory policyFactory = new PolicyFactory();

    static {
        policyMap.put(Policy.SELLER_EXIST, new MerchantExist());
        policyMap.put(Policy.SELLER_NOT_EXIST, new MerchantNotExist());
        policyMap.put(Policy.MERCHANDISE_EXIST,new MerchantExist.MerchandiseExist());
        policyMap.put(Policy.MERCHANDISE_NOT_EXIST,new MerchantExist.MerchandiseNotExist());
    }

    public static PolicyFactory getInstance() {
        return policyFactory;
    }

    public AddMerchandisePolicy getPolicy(Cart cart) {
        return (AddMerchandisePolicy) (cart == null ? policyMap.get(Policy.SELLER_NOT_EXIST) : policyMap.get(Policy.SELLER_EXIST));
    }
    public MerchandisePolicy getGoodsPolicy(Policy policy){
        return (MerchandisePolicy) policyMap.get(policy);
    }
}

