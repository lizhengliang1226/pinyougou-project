package com.pinyougou.solrutil;


import com.alibaba.fastjson.JSON;
import com.pinyougou.config.SpringConfiguration;
import com.pinyougou.mapper.TbItemMapper;

import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Map;

/**
 * @Description 导入所有数据库数据到solr的工具类
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
//        solrTemplate.deleteById("1369301");
        solrTemplate.commit();
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        SolrUtil bean = applicationContext.getBean(SolrUtil.class);
        bean.importData();
    }
}
