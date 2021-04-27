package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.28-2:00
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {
    @Autowired
    private SolrTemplate solrTemplate;
    @Override
    public Map search(Map searchMap) {
        Map resultMap=new HashMap();
        String o = (String) searchMap.get("keywords");
        Query query=new SimpleQuery("*:*");
        Criteria criteria=new Criteria("item_keywords").is(o);
        query.addCriteria(criteria);
        ScoredPage<TbItem> tbItems = solrTemplate.queryForPage(query, TbItem.class);
        List<TbItem> content = tbItems.getContent();
        resultMap.put("rows",content);
        return resultMap;
    }
}
