package com.pinyougou.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.01-1:26
 */
@Service
public class ItemPageServiceImpl implements ItemPageService {
    @Autowired
    private FreeMarkerConfig configurer;
    @Autowired
    private TbGoodsMapper goodsMapper;
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Autowired
    private TbItemMapper itemMapper;
    @Value("${pageDir}")
    private String pageDir;

    @Override
    public boolean generateItemHtml(Long goodsId) throws IOException {
        Configuration configuration = configurer.getConfiguration();
        Template template = configuration.getTemplate("item.ftl");
        Map dataModel = new HashMap();
        //查询商品信息
        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(goodsId);
        dataModel.put("goods", tbGoods);
        //查询商品描述信息
        TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
        dataModel.put("goodsDesc", tbGoodsDesc);
        //显示分类面包屑
        TbItemCat tbItemCat1 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id());
        TbItemCat tbItemCat2 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id());
        TbItemCat tbItemCat3 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id());
        dataModel.put("itemCat1", tbItemCat1.getName());
        dataModel.put("itemCat2", tbItemCat2.getName());
        dataModel.put("itemCat3", tbItemCat3.getName());
        //生成spu对应的所有sku
        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        criteria.andStatusEqualTo("1");
        tbItemExample.setOrderByClause("is_default desc");
        List<TbItem> tbItems = itemMapper.selectByExample(tbItemExample);
        dataModel.put("skuList",tbItems);
//        Writer writer = new FileWriter(new File(pageDir + goodsId + ".html"));
        OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(new File(pageDir + goodsId + ".html")), StandardCharsets.UTF_8);
        try {
            template.process(dataModel, writer);
            writer.close();
        } catch (TemplateException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteHtml(Long goodsId) {
        File file = new File(pageDir + goodsId + ".html");
        return file.delete();
    }

}
