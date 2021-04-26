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
 * @Date 2021.04.26-0:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-redis.xml")
public class RedisTestList {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testSetVal(){
        redisTemplate.boundListOps("list").leftPushAll("a","b","c");
        redisTemplate.boundListOps("list").rightPushAll("a","b","c");
        System.out.println(redisTemplate.boundListOps("list").leftPop());
        System.out.println(redisTemplate.boundListOps("list").rightPop());
        System.out.println(redisTemplate.boundListOps("list").range(0, 3));
        System.out.println(redisTemplate.boundListOps("list").index(2));
        redisTemplate.boundListOps("list").remove(5,"a");
        System.out.println(redisTemplate.boundListOps("list").range(0, 100));
        redisTemplate.delete("list");
    }
}
