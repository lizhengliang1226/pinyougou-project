package com.pinyougou.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pageentity.Result;
import com.pinyougou.pojo.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
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
    public Result generateItemHtml(Long goodsId) throws IOException {
        //查询商品信息
        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(goodsId);
        if (tbGoods == null) return new Result(false, goodsId + "查找不到spu！");
        //查询商品描述信息
        TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
        if (tbGoodsDesc == null) return new Result(false, goodsId + "查找不到商品描述信息");
        //显示分类面包屑
        if (tbGoods.getCategory1Id() == null) return new Result(false, goodsId + "查找不到一级分类id");
        if (tbGoods.getCategory2Id() == null) return new Result(false, goodsId + "查找不到二级分类id");
        if (tbGoods.getCategory3Id() == null) return new Result(false, goodsId + "查找不到三级分类id");
        TbItemCat tbItemCat1 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id());
        TbItemCat tbItemCat2 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id());
        TbItemCat tbItemCat3 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id());
        //生成spu对应的所有sku
        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        criteria.andStatusEqualTo("1");
        tbItemExample.setOrderByClause("is_default desc");
        List<TbItem> tbItems = itemMapper.selectByExample(tbItemExample);
        if (tbItems.size() == 0) return new Result(false, goodsId + "没有sku，无法生成商品详细页");
        //初始化模板,传入相关信息
        Configuration configuration = configurer.getConfiguration();
        Template template = configuration.getTemplate("item.ftl");
        Map dataModel = new HashMap();
        dataModel.put("goods", tbGoods);
        dataModel.put("goodsDesc", tbGoodsDesc);
        dataModel.put("itemCat1", tbItemCat1.getName());
        dataModel.put("itemCat2", tbItemCat2.getName());
        dataModel.put("itemCat3", tbItemCat3.getName());
        dataModel.put("skuList", tbItems);
//        Writer writer = new FileWriter(new File(pageDir + goodsId + ".html"));
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File(pageDir + goodsId + ".html")), StandardCharsets.UTF_8);
        try {
            template.process(dataModel, writer);
            writer.close();
        } catch (TemplateException e) {
            e.printStackTrace();
            return new Result(false, goodsId + "页面生成失败");
        }
        return new Result(true, "创建商品详情页成功");
    }

    @Override
    public boolean deleteHtml(Long goodsId) {
        File file = new File(pageDir + goodsId + ".html");
        return file.delete();
    }

}
