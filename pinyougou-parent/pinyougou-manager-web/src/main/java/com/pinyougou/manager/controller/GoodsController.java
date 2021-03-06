package com.pinyougou.manager.controller;
import java.util.List;

import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pageentity.Result;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojogroup.Goods;
import com.pinyougou.sellergoods.service.GoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/tb_goods")
public class GoodsController {

	@Reference
	private GoodsService goodsService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows,@RequestBody TbGoods tbGoods){
		return goodsService.findPage(page, rows);
	}
	

	
	/**
	 * 修改
	 * @param tbGoods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Goods tbGoods){
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
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Goods findOne(Long id){
		return goodsService.findOne(id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			goodsService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}

	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods,int page,int rows){
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		if (name.equals("admin")){
			return goodsService.findPage(goods, page, rows);
		}
		goods.setSellerId(name);
		return goodsService.findPage(goods, page, rows);
	}
	@RequestMapping("/updateStatus")
	public Result updateStatus(Long[] ids, String status) {
		try {
		goodsService.updateStatus(ids,status);
			return new Result(true,"更新成功");
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false,"更新失败");
		}
	}
}
