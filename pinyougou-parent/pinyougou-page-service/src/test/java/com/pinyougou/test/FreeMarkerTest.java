package com.pinyougou.test;

import com.alibaba.fastjson.JSON;
import com.pinyougou.page.service.ItemPageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.01-2:09
 */

public class FreeMarkerTest {

    @Test
    public void test() throws IOException {
    String s="[{\"color\":\"陶瓷黑\",\"url\":\"http://192.168.182.134/group1/M00/00/00/wKi2hmCMdOCADS1bAACg3ULtnTU165.jpg\"}]";
        List<Map> list = JSON.parseObject(s, List.class);
        System.out.println(list.get(0).get("url"));
    }
}
