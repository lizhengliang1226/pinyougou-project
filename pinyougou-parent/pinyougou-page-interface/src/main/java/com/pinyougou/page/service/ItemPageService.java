package com.pinyougou.page.service;

import java.io.IOException;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.01-1:25
 */
public interface ItemPageService  {
    /**
     * 通过spu的id生成详情也买你
     * @param goodsId
     * @return
     */
    public boolean generateItemHtml(Long goodsId) throws IOException;

    /**
     * 当商品下架时，删除对应的sku静态文件
     * @param goodsId
     * @return
     */
    public boolean deleteHtml(Long goodsId);
}
