package com.pinyougou.pojogroup;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.21-0:34
 */
@Data
public class Goods implements Serializable {
    private TbGoods goods;
    private TbGoodsDesc goodsDesc;
}
