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
@ContextConfiguration("classpath:spring/applicationContext-redis.xml")
public class RedisHashTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testSetVal(){
        redisTemplate.boundHashOps("map").put("name","lzl");
        System.out.println(redisTemplate.boundHashOps("map").get("name"));
        redisTemplate.boundHashOps("map").delete("name");
    }
}
