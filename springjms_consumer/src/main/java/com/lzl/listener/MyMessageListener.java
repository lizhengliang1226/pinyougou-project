package com.lzl.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.transform.Source;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.02-4:52
 */
public class MyMessageListener  implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage= (TextMessage) message;

        try {
            System.out.println(textMessage.getText());
            Thread.sleep(1000000000);
        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
