package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.order.service.OrderService;
import com.pinyougou.pageentity.Result;
import com.pinyougou.pay.service.WeixinPayService;
import com.pinyougou.pojo.TbPayLog;
import org.apache.solr.common.util.Hash;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.IdWorker;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.15-13:39
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Reference(timeout = 1000 * 60)
    private WeixinPayService weixinPayService;

    @Reference
    private OrderService orderService;

    /**
     * 创建订单支付的二维码，生成二维码的url，但是我的是没有的，因为我无法开通微信支付功能
     * @return 包含支付订单号和二维码url，还有支付金额
     */
    @RequestMapping("/createNative")
    public Map createNative() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        TbPayLog payLog = orderService.searchPayLogFromRedis(userId);
        if (payLog != null) {
            return weixinPayService.createNative(payLog.getOutTradeNo() + "", payLog.getTotalFee() + "");
        } else {
            return new HashMap<>();
        }
    }

    /**
     * 查询支付状态
     *
     * @param out_trade_no 支付订单号
     * @return 订单支付状态
     */
    @RequestMapping("/queryPayStatus")
    public Result queryPayStatus(String out_trade_no) {
        Map map = weixinPayService.queryPayStatusWhile(out_trade_no);
        if (map == null) {
            System.out.println("返回为空");
            return new Result(true, "支付失败");
        } else {
            if ("SUCCESS".equals(map.get("trade_state"))) {
                System.out.println("支付成功");
                orderService.updateOrderStatus(out_trade_no, (String) map.get("transaction_id"));
                return new Result(true, "支付成功");
            } else {
                /*
                * 这里更新订单状态是因为我不能申请微信支付这个功能，所以请求肯定失败
                * 我设置的6秒时间，超时后就会来这，所以我在这里更新订单状态，主要是为了做测试
                * 实际应该是在上面那个
                * */
                System.out.println("支付超时");
                orderService.updateOrderStatus(out_trade_no, "8888888888");
                return new Result(true, "支付超时");
            }
        }
    }
}
