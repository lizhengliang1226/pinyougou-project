package com.pinyougou.pojogroup;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.21-0:34
 */
@Data
public class Goods implements Serializable {
    private TbGoods goods;
    private TbGoodsDesc goodsDesc;
    private List<TbItem> itemList;
}
