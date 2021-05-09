package com.lzl;

import static org.junit.Assert.assertTrue;

import com.lzl.listener.MyMessageListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:*.xml")
public class AppTest 
{

    @Test
    public void shouldAnswerWithTrue() throws InterruptedException {
       Thread.sleep(1000000000);
    }
}
