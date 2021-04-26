package com.pinyougou.sellergoods.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.*;
import com.pinyougou.pojogroup.Goods;
import com.pinyougou.sellergoods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;


/**
 * 品牌业务逻辑层
 *
 * @author Administrator
 */
@Transactional
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private TbGoodsMapper tbGoodsMapper;
    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;
    @Autowired
    private TbItemMapper itemMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbGoods> findAll() {
        return tbGoodsMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbGoods> page = (Page<TbGoods>) tbGoodsMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(Goods tbGoods) {
        tbGoods.getGoods().setAuditStatus("0");//审核状态
        tbGoods.getGoods().setIsMarketable("1");
        tbGoodsMapper.insert(tbGoods.getGoods());
        tbGoods.getGoodsDesc().setGoodsId(tbGoods.getGoods().getId());
        tbGoodsDescMapper.insert(tbGoods.getGoodsDesc());
        List<TbItem> itemList = tbGoods.getItemList();
        insertItem(tbGoods, itemList);
    }


    /**
     * 修改
     */
    @Override
    public void update(Goods tbGoods) {
        tbGoodsMapper.updateByPrimaryKey(tbGoods.getGoods());
        tbGoodsDescMapper.updateByPrimaryKey(tbGoods.getGoodsDesc());
        List<TbItem> itemList = tbGoods.getItemList();
        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andGoodsIdEqualTo(tbGoods.getGoods().getId());
        itemMapper.deleteByExample(tbItemExample);
        insertItem(tbGoods, itemList);
    }

    private void insertItem(Goods tbGoods, List<TbItem> itemList) {
        itemList.forEach(item -> {
            if ("1".equals(tbGoods.getGoods().getIsEnableSpec())) {
                AtomicReference<String> title = new AtomicReference<>(tbGoods.getGoods().getGoodsName());
                Map<String, Object> map = JSON.parseObject(item.getSpec());
                map.forEach((k,v)->{
                    title.updateAndGet(v1 -> v1 + v);
                });
                item.setTitle(title.get());
                setItemValues(item,tbGoods);
            }else{
                TbItem tbItem=new TbItem();
                tbItem.setTitle(tbGoods.getGoods().getGoodsName());
                tbItem.setSellPoint("无");
                tbItem.setPrice(tbGoods.getGoods().getPrice());
                tbItem.setNum(9999);
                tbItem.setIsDefault("1");
                tbItem.setCategoryid(tbGoods.getGoods().getCategory3Id());
                tbItem.setSpec("{}");
                tbItem.setStatus("1");
                tbItem.setCreateTime(new Date());
                tbItem.setUpdateTime(new Date());
                itemMapper.insert(tbItem);
            }

        });
    }

    private void setItemValues(TbItem tbItem, Goods tbGoods) {
        tbItem.setGoodsId(tbGoods.getGoods().getId());
        tbItem.setSellerId(tbGoods.getGoods().getSellerId());
        tbItem.setCategoryid(tbGoods.getGoods().getCategory3Id());
        tbItem.setCreateTime(new Date());
        tbItem.setUpdateTime(new Date());
        tbItem.setStatus("1");
        itemMapper.insert(tbItem);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Goods findOne(Long id) {
        Goods goods = new Goods();
        TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(id);
        TbGoodsDesc tbGoodsDesc = tbGoodsDescMapper.selectByPrimaryKey(id);
        goods.setGoods(tbGoods);
        goods.setGoodsDesc(tbGoodsDesc);
        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        List<TbItem> tbItems = itemMapper.selectByExample(tbItemExample);
        goods.setItemList(tbItems);
        return goods;
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for(Long id : ids) {
//            tbGoodsMapper.deleteByPrimaryKey(id);
            TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(id);
            tbGoods.setIsDelete("1");
            tbGoodsMapper.updateByPrimaryKey(tbGoods);
        }
    }

    @Override
    public PageResult findPage(TbGoods tbGoods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        TbGoodsExample tbGoodsExample = new TbGoodsExample();
        TbGoodsExample.Criteria criteria = tbGoodsExample.createCriteria();
        criteria.andIsDeleteIsNull();
        if (ObjectUtil.isNotNull(tbGoods)) {
            String goodsName = tbGoods.getGoodsName();
            String sellerId = tbGoods.getSellerId();
            String auditStatus = tbGoods.getAuditStatus();
            if (StrUtil.isNotEmpty(goodsName)) {
                criteria.andGoodsNameLike("%" + goodsName + "%");
            }
            if (StrUtil.isNotEmpty(sellerId)) {
                criteria.andSellerIdEqualTo(sellerId);
            }
            if (StrUtil.isNotEmpty(auditStatus)) {
                criteria.andAuditStatusEqualTo(auditStatus);
            }

        }
        Page<TbGoods> page = (Page<TbGoods>) tbGoodsMapper.selectByExample(tbGoodsExample);
        List<TbGoods> result = page.getResult();
        for(TbGoods goods : result) {
            System.out.println(goods.getGoodsName());
        }
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        for(Long id : ids) {
            TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(id);
            tbGoods.setAuditStatus(status);
            tbGoodsMapper.updateByPrimaryKey(tbGoods);
        }
    }

    @Override
    public void updateMarkStatus(Long[] ids, String status) {
        for(Long id : ids) {
            TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(id);
            tbGoods.setIsMarketable(status);
            tbGoodsMapper.updateByPrimaryKey(tbGoods);
        }
    }

}
