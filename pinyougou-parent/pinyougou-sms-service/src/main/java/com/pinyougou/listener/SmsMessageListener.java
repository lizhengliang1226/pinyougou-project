package com.pinyougou.listener;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.pinyougou.util.SmsUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.09-12:32
 */
public class SmsMessageListener implements MessageListener {
    @Autowired
    private SmsUtil smsUtil;

    @Value("${TEMPLATE_SMS_CODE}")
    private String templateCode;

    @Value("${TEMPLATE_PARAM_SMS_CODE}")
    private String templateParam;

    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        String phone = mapMessage.getString("phone");
        String code = mapMessage.getString("smsCode");
        String param = templateParam.replace("[value]", code);
        System.out.println("phone is :" + phone + "  code is :" + code);
        SendSmsResponse sendSmsResponse = smsUtil.sendSms(phone, templateCode, param);
        System.out.println("发送响应码为：" + sendSmsResponse.getCode());
        System.out.println("发送消息返回信息：" + sendSmsResponse.getMessage());
    }
}
