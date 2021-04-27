package com.lzl;


import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.27-23:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/*")
public class SolrTest {
    @Autowired
    private SolrTemplate solrTemplate;
    /**
     * Rigorous Test :-)
     */
    @After
    public void after(){
        solrTemplate.commit();
    }
    @Test
    public void shouldAnswerWithTrue()
    {
        List<TbItem> list=new ArrayList<>();
        for(int i = 0; i < 300; i++) {
            TbItem tbItem = new TbItem();
            tbItem.setBrand("小米"+i);
            tbItem.setCategory("手机"+i);
            tbItem.setId(1123L+i);
            tbItem.setImage("http://192.168.182.134/group1/M00/00/00/wKi2hmCG7s-AXdv-ABGOl-cvbPo057.jpg");
            tbItem.setPrice(new BigDecimal(125.2));
            tbItem.setSeller("小米旗舰店"+i);
            tbItem.setTitle("小米手机11"+i);
            list.add(tbItem);
        }
solrTemplate.saveBeans(list);
    }
    @Test
    public void testSelect(){
        TbItem byId = solrTemplate.getById(1123, TbItem.class);
        System.out.println(byId);
    }
    @Test
    public void testSelect1(){
         solrTemplate.deleteById("1123");
        TbItem byId = solrTemplate.getById(1123, TbItem.class);
        System.out.println(byId);
    }
    @Test
    public void testSelect11(){
        Query query=new SimpleQuery("*:*");
        query.setOffset(10);
        query.setRows(20);
        Criteria criteria=new Criteria("item_title").contains("2");
        query.addCriteria(criteria);
        ScoredPage<TbItem> tbItems = solrTemplate.queryForPage(query, TbItem.class);
        tbItems.getContent().forEach(s->{
            System.out.println(s.getTitle());
        });
        System.out.println(tbItems.getTotalElements());

    }
    @Test
    public void del(){
        Query query=new SimpleQuery("*:*");
        query.setOffset(10);
        query.setRows(20);
        Criteria criteria=new Criteria("item_title").contains("2");
        query.addCriteria(criteria);
        solrTemplate.delete(query);
    }
}
