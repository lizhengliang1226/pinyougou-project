package com.pinyougou.solrutil;


import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbItemMapper;

import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.28-0:54
 */
@Component
public class SolrUtil {
    private TbItemMapper itemMapper;
    private SolrTemplate solrTemplate;

    @Autowired
    public void setSolrTemplate(SolrTemplate solrTemplate) {
        this.solrTemplate = solrTemplate;
    }

    @Autowired
    public void setItemMapper(TbItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public void importData() {
        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andStatusEqualTo("1");
        List<TbItem> tbItems = itemMapper.selectByExample(tbItemExample);
        tbItems.forEach(item -> {
            Map<String, String> spec = JSON.parseObject(item.getSpec(), Map.class);
            item.setSpecMap(spec);
        });
        solrTemplate.saveBeans(tbItems);
        solrTemplate.commit();
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
        SolrUtil bean = applicationContext.getBean(SolrUtil.class);
        bean.importData();
    }
}
