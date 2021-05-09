package com.lzl.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.01-6:36
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-jms-producer.xml"})
public class TestJms {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination queueTextDestination;
    @Autowired
    private Destination topicTextDestination;
    @Test
    public void test(){
        jmsTemplate.convertAndSend(queueTextDestination,"hello你好");
    }
    @Test
    public void send(){
        jmsTemplate.convertAndSend(topicTextDestination,"word");
    }
}
