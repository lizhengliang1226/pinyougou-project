package com.pinyougou.search.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.GroupPage;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;

import java.util.ArrayList;
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

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbItemMapper itemMapper;

    @Value("${ITEM_CAT_REDIS_NAME}")
    private String ITEM_CAT_REDIS_NAME;

    @Value("${BRAND_LIST_REDIS_NAME}")
    private String BRAND_LIST_REDIS_NAME;

    @Value("${SPEC_LIST_REDIS_NAME}")
    private String SPEC_LIST_REDIS_NAME;

    @Override
    public Map<String, Object> search(Map searchMap) {
        //查询商品列表，键为rows
        Map<String, Object> resultMap = new HashMap<>(searchList(searchMap));
        //查询分类列表
        List<String> categoryList = searchCategoryList(searchMap);
        resultMap.put("categoryList", categoryList);
        //根据分类查询规格和品牌列表
        if (StrUtil.isNotEmpty((CharSequence) searchMap.get("category"))) {
            resultMap.putAll(searchBrandAndSpecList((String) searchMap.get("category")));
        } else {
            if (categoryList.size() > 0) {
                resultMap.putAll(searchBrandAndSpecList(categoryList.get(0)));
            }
        }
        return resultMap;
    }

    /**
     * 查询分类列表
     *
     * @param searchMap 查询实体
     * @return 所有根据关键字查询出来的分类列表
     */
    private List<String> searchCategoryList(Map searchMap) {
        List<String> list = new ArrayList<>();
        //关键字查询
        Query query = getQuery(searchMap);
        //分组查询，根据类别分组
        GroupOptions itemCategory = new GroupOptions().addGroupByField("item_category");
        query.setGroupOptions(itemCategory);
        GroupPage<TbItem> tbItems = solrTemplate.queryForGroupPage(query, TbItem.class);
        tbItems.getGroupResult("item_category").getGroupEntries().getContent().forEach(groupEntry -> {
            list.add(groupEntry.getGroupValue());
        });
        return list;
    }

    /**
     * 查询商品列表
     *
     * @param searchMap 查询实体
     * @return 查询结果集map，键为rows,值为结果集List<TbItem>集合
     */
    private Map<String, Object> searchList(Map<String, Object> searchMap) {
        Map<String, Object> resultMap = new HashMap<>();
        //关键字查询
        HighlightQuery query = getQuery(searchMap);
        //商品分类过滤查询
        propertyFilter(searchMap, query, "category", "item_category");
        //商品品牌过滤查询
        propertyFilter(searchMap, query, "brand", "item_brand");
        //商品价格过滤查询
        priceFilter(searchMap, query);
        //商品规格过滤查询
        specFilter(searchMap, query);
        //分页查询
        pageQuery(searchMap, query);
        //排序
        sortByField(searchMap, query);
        //高亮显示查询
        //<em style='color:red;'></em>
        HighlightPage<TbItem> tbItems = getTbItemHighlightPage(query);
        List<TbItem> content = tbItems.getContent();
        resultMap.put("rows", content);
        resultMap.put("total", tbItems.getTotalElements());
        resultMap.put("totalPages", tbItems.getTotalPages());
        return resultMap;
    }

    /**
     * 把title字段高亮显示
     *
     * @param query
     * @return
     */
    private HighlightPage<TbItem> getTbItemHighlightPage(HighlightQuery query) {
        HighlightOptions highlightOptions = new HighlightOptions()
                .addField("item_title")
                .setSimplePrefix("<em style='color:red;'>")
                .setSimplePostfix("</em>");
        query.setHighlightOptions(highlightOptions);
        HighlightPage<TbItem> tbItems = solrTemplate.queryForHighlightPage(query, TbItem.class);
        tbItems.getHighlighted().forEach(highlightEntry -> {
            TbItem entity = highlightEntry.getEntity();
            List<HighlightEntry.Highlight> highlights = highlightEntry.getHighlights();
            if (ObjectUtil.isNotEmpty(highlights) && ObjectUtil.isNotEmpty(highlights.get(0).getSnipplets())) {
                entity.setTitle(highlights.get(0).getSnipplets().get(0));
            }
        });
        return tbItems;
    }

    /**
     * 根据指定的字段排序
     *
     * @param searchMap
     * @param query
     */
    private void sortByField(Map<String, Object> searchMap, Query query) {
        String sortType = (String) searchMap.get("sortType");
        String sortField = (String) searchMap.get("sortField");
        if (StrUtil.isNotEmpty(sortType)) {
            if (sortType.equals("asc") || sortType.equals("desc")) {
                Sort sort = new Sort(sortType.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "item_" + sortField);
                query.addSort(sort);
            }
        }
    }

    /**
     * 分页查询，默认为第一页，20条记录
     *
     * @param searchMap 查询实体
     * @param query     查询对象
     */
    private void pageQuery(Map<String, Object> searchMap, Query query) {
        Integer pageNum = (Integer) searchMap.get("pageNum");
        Integer pageSize = (Integer) searchMap.get("pageSize");
        if (ObjectUtil.isNull(pageNum)) {
            pageNum = 1;
        }
        if (ObjectUtil.isNull(pageSize)) {
            pageSize = 20;
        }
        query.setOffset((pageNum - 1) * pageSize);
        query.setRows(pageSize);
    }

    /**
     * 规格过滤查询
     *
     * @param searchMap 查询实体
     * @param query     solr的查询对象
     */
    private void specFilter(Map<String, Object> searchMap, Query query) {
        if (ObjectUtil.isNotNull(searchMap.get("spec"))) {
            Map<String, String> specMap = (Map<String, String>) searchMap.get("spec");
            specMap.forEach((k, v) -> {
                Criteria filter = new Criteria("item_spec_" + k).is(v);
                FilterQuery filterQuery = new SimpleFilterQuery(filter);
                query.addFilterQuery(filterQuery);
            });
        }
    }

    /**
     * 价格区间过滤
     *
     * @param searchMap 查询实体
     * @param query     solr查询对象
     */
    private void priceFilter(Map<String, Object> searchMap, Query query) {
        if (StrUtil.isNotEmpty((CharSequence) searchMap.get("price"))) {
            String price = (String) searchMap.get("price");
            String[] split = price.split("-");
            Criteria filter;
            if (split[1].equals("#")) {
                filter = new Criteria("item_price").greaterThanEqual(split[0]);
            } else {
                filter = new Criteria("item_price").between(split[0], split[1]);
            }
            FilterQuery filterQuery = new SimpleFilterQuery(filter);
            query.addFilterQuery(filterQuery);
        }
    }

    /**
     * 单属性过滤
     *
     * @param searchMap    查询实体
     * @param query        solr查询对象
     * @param propertyName 属性名
     * @param fieldName    对应的solr域中的字段名
     */
    private void propertyFilter(Map<String, Object> searchMap, Query query, String propertyName, String fieldName) {
        if (StrUtil.isNotEmpty((CharSequence) searchMap.get(propertyName))) {
            Criteria filter = new Criteria(fieldName).is(searchMap.get(propertyName));
            FilterQuery filterQuery = new SimpleFilterQuery(filter);
            query.addFilterQuery(filterQuery);
        }
    }

    /**
     * 关键字搜索
     *
     * @param searchMap 查询实体
     * @return 根据关键字查询得到的query对象
     */
    private HighlightQuery getQuery(Map searchMap) {
        String o = (String) searchMap.get("keywords");
        o = o.replace(" ", "");
        searchMap.put("keywords", o);
        HighlightQuery query = new SimpleHighlightQuery();
        Criteria criteria = new Criteria("item_keywords").is(o);
        query.addCriteria(criteria);
        return query;
    }

    /**
     * 通过商品分类名称查询规格列表和品牌列表
     *
     * @param categoryName 分类名称
     * @return 根据分类名称查到的规格列表和品牌列表
     */
    private Map<String, Object> searchBrandAndSpecList(String categoryName) {
        Map<String, Object> map = new HashMap();
        Long typeId = (Long) redisTemplate.boundHashOps(ITEM_CAT_REDIS_NAME).get(categoryName);
        if (ObjectUtil.isNotNull(typeId)) {
            List<Map<String, Object>> specList = (List<Map<String, Object>>) redisTemplate.boundHashOps(SPEC_LIST_REDIS_NAME).get(typeId);
            List<Map<String, Object>> brandList = (List<Map<String, Object>>) redisTemplate.boundHashOps(BRAND_LIST_REDIS_NAME).get(typeId);
            map.put(SPEC_LIST_REDIS_NAME, specList);
            map.put(BRAND_LIST_REDIS_NAME, brandList);
        }
        return map;
    }

    @Override
    public void importList(List list) {
        solrTemplate.saveBeans(list);
        solrTemplate.commit();
    }

    @Override
    public void deleteByGoodsIds(Long[] goodsIds) {
        for(Long goodsId : goodsIds) {
            List<TbItem> tbItems = getItemListByGoodsId(goodsId);
            List<String> ids = new ArrayList<>();
            tbItems.forEach(item -> ids.add(String.valueOf(item.getId())));
            solrTemplate.deleteById(ids);
            solrTemplate.commit();
        }
    }

    /**
     * 通过goods_id字段查找所有的item
     *
     * @param goodsId
     * @return
     */
    private List<TbItem> getItemListByGoodsId(Long goodsId) {
        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        List<TbItem> tbItems = itemMapper.selectByExample(tbItemExample);
        return tbItems;
    }
}
