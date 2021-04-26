package com.pinyougou.sellergoods.service;

import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbBrand;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.16-16:28
 */
public interface BrandService {
    /**
     * 查找所有品牌信息
     * @return
     */
    public List<TbBrand> findAll();

    /**
     * 分页查找
     * @param currentPage 当前页
     * @param pageSize  页面大小
     * @return
     */
    public PageResult findPage(int currentPage,int pageSize);

    /**
     * 添加品牌
     * @param tbBrand
     */
    public boolean addBrand(TbBrand tbBrand);

    /**
     * 查找品牌通过id
     * @param id
     * @return
     */
    public TbBrand findById(long id);

    /**
     * 更新
     * @param tbBrand
     * @return
     */
    public boolean update(TbBrand tbBrand);

    /**
     * 删除多个品牌
     * @param ids
     * @return
     */
    public boolean delete(long[] ids);
    /**
     * 分页查找
     * @param currentPage 当前页
     * @param pageSize  页面大小
     * @return
     */
    public PageResult findPage(TbBrand tbBrand,int currentPage,int pageSize);
    public List<Map> selectOptionList();
}
