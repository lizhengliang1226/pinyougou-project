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
 * @Date 2021.04.26-0:03
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/*")
public class RedisSetDemo {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testSet(){
        redisTemplate.boundSetOps("set").add("a","b","c");
    }
    @Test
    public void testGet(){
        System.out.println(redisTemplate.boundSetOps("set").members());
    }
    @Test
    public void testRemove(){
    redisTemplate.boundSetOps("set").remove("a","b");
    }
    @Test
    public void testDel(){
        redisTemplate.delete("set");
    }
}
