package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pageentity.Result;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.sellergoods.service.GoodsDescService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/tb_goods_desc")
public class GoodsDescController {

	@Reference
	private GoodsDescService goodsDescService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoodsDesc> findAll(){
		return goodsDescService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return goodsDescService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param tb_goods_desc
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbGoodsDesc tb_goods_desc){
		try {
			goodsDescService.add(tb_goods_desc);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param tbGoodsDesc
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbGoodsDesc tbGoodsDesc){
		try {
			goodsDescService.update(tbGoodsDesc);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbGoodsDesc findOne(Long id){
		return goodsDescService.findOne(id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			goodsDescService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
	
}
