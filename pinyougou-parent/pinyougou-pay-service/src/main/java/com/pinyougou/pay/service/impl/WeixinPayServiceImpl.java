package com.pinyougou.pay.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import com.pinyougou.pay.service.WeixinPayService;
import org.springframework.beans.factory.annotation.Value;
import util.HttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.15-13:13
 */
@Service
public class WeixinPayServiceImpl implements WeixinPayService {
    /*appid=wx8397f8696b538317
partner=1473426802
partnerkey=8A627A4578ACE384017C997F12D68B23*/
    @Value("${appid}")
    private String appid;
    @Value("${partner}")
    private String partner;
    @Value("${partnerkey}")
    private String partnerkey;

    @Override
    public Map createNative(String out_trade_no, String total_fee) {
        //构建参数
        Map paramMap = getMap(out_trade_no, total_fee);
        try {
            String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
            Map<String, String> resultMap = getResultMap(paramMap, url);
            Map map = new HashMap();
            map.put("code_url", resultMap.get("code_url"));
            map.put("out_trade_no", out_trade_no);
            map.put("total_fee", total_fee);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap();
        }
    }

    private Map getMap(String out_trade_no, String total_fee) {
        Map paramMap = new HashMap();
        paramMap.put("appid", appid);//公众号id
        paramMap.put("mch_id", partner);//商户号id
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
        paramMap.put("body", "品优购");//商品描述
        paramMap.put("out_trade_no", out_trade_no);//订单号
        paramMap.put("total_fee", total_fee);//总金额
        paramMap.put("spbill_create_ip", "127.0.0.1");//终端IP
        paramMap.put("notify_url", "http://pay.pinyougou.com/notify");//通知地址
        paramMap.put("trade_type", "NATIVE");//交易类型
        return paramMap;
    }

    @Override
    public Map queryPayStatus(String out_trade_no) {
        Map paramMap = new HashMap();
        paramMap.put("appid", appid);//公众号id
        paramMap.put("mch_id", partner);//商户号id
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
        paramMap.put("out_trade_no", out_trade_no);//订单号
        try {
            String url = "https://api.mch.weixin.qq.com/pay/orderquery";
            return getResultMap(paramMap, url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Map queryPayStatusWhile(String out_trade_no) {
        Map map;
        int x = 0;
        //轮询五分钟
        while(true) {
            x++;
            map = queryPayStatus(out_trade_no);
            if (ObjectUtil.isEmpty(map) || x >= 2 || "SUCCESS".equals(map.get("trade_state"))) {
                break;
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    private Map<String, String> getResultMap(Map paramMap, String url) throws Exception {
        String paramXml = WXPayUtil.generateSignedXml(paramMap, partnerkey);
//        System.out.println("请求的xml" + paramXml);
        HttpClient httpClient = new HttpClient(url);
        httpClient.setHttps(true);
        httpClient.setXmlParam(paramXml);
        //发送请求
        httpClient.post();
        String resultXml = httpClient.getContent();
//        System.out.println("返回的xml" + resultXml);
        return WXPayUtil.xmlToMap(resultXml);
    }
}
