package com.lzl;

import com.lzl.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.27-23:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:*")
public class SolrTest {
    @Autowired
    private SolrTemplate solrTemplate;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        TbItem tbItem = new TbItem();
        tbItem.setBrand("小米");
        tbItem.setCategory("手机");
        tbItem.setId(1123L);
        tbItem.setImage("http://192.168.182.134/group1/M00/00/00/wKi2hmCG7s-AXdv-ABGOl-cvbPo057.jpg");
        tbItem.setPrice(new BigDecimal(125.2));
        tbItem.setSeller("小米旗舰店");
        tbItem.setTitle("小米手机11");
        solrTemplate.saveBean(tbItem);
        solrTemplate.commit();
    }
}
