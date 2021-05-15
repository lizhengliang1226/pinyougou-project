package com.pinyougou.cart.controller;


import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pageentity.Result;
import com.pinyougou.pojo.TbAddress;
import com.pinyougou.user.service.AddressService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;

import java.util.List;

/**
 * controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/tb_address")
public class AddressController {

    @Reference
    private AddressService tb_addressService;

    /**
     * 增加
     *
     * @param tb_address
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody TbAddress tb_address) {
        try {
            tb_addressService.add(tb_address);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param tb_address
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbAddress tb_address) {
        try {
            tb_addressService.update(tb_address);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
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
            tb_addressService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 获取当前登录用户的地址列表
     *
     * @return
     */
    @RequestMapping("/findListByLoginUser")
    public List<TbAddress> findListByLoginUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TbAddress> listByUserId = tb_addressService.findListByUserId(name);
        return listByUserId;
    }
}
