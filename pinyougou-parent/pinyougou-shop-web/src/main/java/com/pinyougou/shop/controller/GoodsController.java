package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pageentity.Result;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojogroup.Goods;
import com.pinyougou.search.service.ItemSearchService;
import com.pinyougou.sellergoods.service.GoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/tb_goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;
    @Reference
    private ItemSearchService itemSearchService;
    @Reference
    private ItemPageService itemPageService;
    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbGoods> findAll() {
        return goodsService.findAll();
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return goodsService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param tbGoods
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Goods tbGoods) {
        try {
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            tbGoods.getGoods().setSellerId(sellerId);
            goodsService.add(tbGoods);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param tbGoods
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Goods tbGoods) {
        //判断要修改的商品是否为该商家的商品
        Goods one = goodsService.findOne(tbGoods.getGoods().getId());
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!one.getGoods().getSellerId().equals(sellerId) || !tbGoods.getGoods().getSellerId().equals(sellerId)) {
            return new Result(false, "修改失败,该商品不属于当前商家！");
        }
        try {
            goodsService.update(tbGoods);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public Goods findOne(Long id) {
        return goodsService.findOne(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            goodsService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody TbGoods goods, int page, int rows) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        goods.setSellerId(name);
        PageResult page1 = goodsService.findPage(goods, page, rows);
        return page1;
    }

    @RequestMapping("/updateMarkStatus")
    public Result updateStatus(Long[] ids, String status) {
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            goodsService.updateMarkStatus(ids, status, sellerId);
            if (status.equals("1")) {//上架更新索引库
                List<TbItem> itemListByGoodsIds = goodsService.findItemListByGoodsIds(ids);
                if (itemListByGoodsIds.size() > 0) {
                    itemSearchService.importList(itemListByGoodsIds);
                    //商品上架后，生成详情页
                    for(Long id : ids) {
                        itemPageService.generateItemHtml(id);
                    }
                }
            } else {//下架删除索引库的数据
                itemSearchService.deleteByGoodsIds(ids);
                for(Long id : ids) {
                    itemPageService.deleteHtml(id);
                }
            }
            return new Result(true, "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "更新失败");
        }
    }
    @RequestMapping("/generate")
    public void generateHtml(Long goodsId) throws IOException {
        itemPageService.generateItemHtml(goodsId);
    }

}
