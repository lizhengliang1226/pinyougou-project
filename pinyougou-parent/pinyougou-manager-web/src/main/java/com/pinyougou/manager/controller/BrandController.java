package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pageentity.Result;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.16-16:56
 */
@RestController
@RequestMapping("/brand")
public class BrandController {
    @Reference
    private BrandService brandService;

    @RequestMapping("/brandList")
    public List<TbBrand> findAll() {
        return brandService.findAll();
    }

    @RequestMapping("/pageBrandList")
    public PageResult findPage(@NonNull int page, int size) {
        return brandService.findPage(page, size);
    }

    @RequestMapping("/addBrand")
    public Result addBrand(@RequestBody TbBrand tbBrand) {
        return new Result(brandService.addBrand(tbBrand), "添加成功");
    }

    @RequestMapping("/findById")
    public TbBrand findById(long id) {
        return brandService.findById(id);
    }

    @RequestMapping("/updateBrand")
    public Result updateBrand(@RequestBody TbBrand tbBrand) {
        return new Result(brandService.update(tbBrand), "添加失败 ");
    }

    @RequestMapping("/deleteBrand")
    public Result delete(long[] ids) {
        return new Result(brandService.delete(ids), "删除失败");
    }

    @RequestMapping("/pageConditionQuery")
    public PageResult findPageCondition(@RequestBody TbBrand tbBrand, int page, int size) {
        return brandService.findPage(tbBrand, page, size);
    }
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return brandService.selectOptionList();
    }
}
