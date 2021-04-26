package com.lzl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.25-23:49
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-redis.xml")
public class SpringRedisDemo {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testSetVal(){
        redisTemplate.boundValueOps("a").set("10");
    }
    @Test
    public void testGetVal(){
        System.out.println(redisTemplate.boundValueOps("a").get());
    }
    @Test
    public void testDeleteVal(){
        redisTemplate.delete("a");
    }
}
