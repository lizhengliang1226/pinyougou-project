package com.pinyougou.seckill.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pageentity.Result;
import com.pinyougou.pay.service.WeixinPayService;
import com.pinyougou.pojo.TbSeckillOrder;
import com.pinyougou.seckill.service.SeckillOrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private SeckillOrderService seckillOrderService;

    /**
     * 创建订单支付的二维码，生成二维码的url，但是我的是没有的，因为我无法开通微信支付功能
     * @return 包含支付订单号和二维码url，还有支付金额
     */
    @RequestMapping("/createNative")
    public Map createNative() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        TbSeckillOrder order = seckillOrderService.searchSecKillOrderFromRedisByUserId(userId);
        if (order != null) {
            long fen= (long) (order.getMoney().doubleValue()*100);
            return weixinPayService.createNative(order.getId() + "", fen + "");
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
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (map == null) {
            System.out.println("返回为空");
            return new Result(true, "支付失败");
        } else {
            if ("SUCCESS".equals(map.get("trade_state"))) {
                System.out.println("支付成功");
                seckillOrderService.saveOrderToDb(userId, Long.valueOf(out_trade_no), (String) map.get("transaction_id"));
                return new Result(true, "支付成功");
            } else {
                /*
                * 这里更新订单状态是因为我不能申请微信支付这个功能，所以请求肯定失败
                * 我设置的6秒时间，超时后就会来这，所以我在这里更新订单状态，主要是为了做测试
                * 实际应该是在上面那个
                * */
                System.out.println("支付超时");
                //这句代码是由于我的项目没办法开通微信支付，所以写在这保证成功做测试用
                seckillOrderService.saveOrderToDb(userId, Long.valueOf(out_trade_no), (String) map.get("transaction_id"));
                //超时处理，这个才是真的该写在这里的代码
                //seckillOrderService.deleteOrderFromRedis(userId, Long.valueOf(out_trade_no));
                return new Result(true, "支付超时");
            }
        }
    }
}
