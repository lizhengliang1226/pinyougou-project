package com.pinyougou.config;

import com.pinyougou.mapper.TbItemMapper;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.02-4:00
 */
@Configuration
@ComponentScan("com.pinyougou.solrutil")
@PropertySource("classpath:properties.properties")
@ContextConfiguration("classpath*:spring/*.xml")
public class SpringConfiguration {
    @Value("${SOLR_URL}")
    private String SOLR_URL;

    @Bean("solrTemplate")
    public SolrTemplate getSolrTemplate(){
        SolrServer solrServer = new HttpSolrServer(SOLR_URL);
        return new SolrTemplate(solrServer);
    }


}
