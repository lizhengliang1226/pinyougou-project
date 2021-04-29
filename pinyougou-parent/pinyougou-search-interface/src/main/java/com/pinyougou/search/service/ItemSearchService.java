package com.pinyougou.search.service;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.28-1:57
 */
public interface ItemSearchService {
    public Map<String,Object> search(Map<String,Object> searchMap) ;

    /**
     * 导入sku列表到索引库
     * @param list
     */
    public void importList(List list);

    /**
     * 删除sku列表从索引库
     * @param goodsIds
     */
    public void deleteByGoodsIds(Long[] goodsIds);
}
