package com.lzl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.26-0:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-redis-cluster.xml")
public class RedisHashTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testSetVal(){
        redisTemplate.boundValueOps("lzl").set("123456");
        System.out.println(redisTemplate.boundValueOps("lzl").get());
    }
}
