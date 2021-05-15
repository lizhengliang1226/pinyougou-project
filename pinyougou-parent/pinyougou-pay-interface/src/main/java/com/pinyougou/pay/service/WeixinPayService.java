package com.pinyougou.pay.service;

import java.util.Map;

/**
 * @Description 支付
 * @Author LZL
 * @Date 2021.05.15-13:09
 */
public interface WeixinPayService {
    /**
     * 生成微信支付二维码
     * @param out_trade_no 订单号
     * @param total_fee 总金额
     * @return
     */
    public Map createNative(String out_trade_no,String total_fee);

    /**
     * 查询订单支付状态
     * @param out_trade_no
     * @return
     */
    public Map queryPayStatus(String out_trade_no);
    /**
     * 查询订单支付状态,轮询方式
     * @param out_trade_no
     * @return
     */
    public Map queryPayStatusWhile(String out_trade_no);
}
