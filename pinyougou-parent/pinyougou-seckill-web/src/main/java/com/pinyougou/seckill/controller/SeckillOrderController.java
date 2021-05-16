package com.pinyougou.seckill.controller;
import java.util.List;

import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pageentity.Result;
import com.pinyougou.seckill.service.SeckillOrderService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeckillOrder;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/tb_seckill_order")
public class SeckillOrderController {

	@Reference
	private SeckillOrderService tb_seckill_orderService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeckillOrder> findAll(){			
		return tb_seckill_orderService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return tb_seckill_orderService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param tb_seckill_order
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbSeckillOrder tb_seckill_order){
		try {
			tb_seckill_orderService.add(tb_seckill_order);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param tb_seckill_order
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbSeckillOrder tb_seckill_order){
		try {
			tb_seckill_orderService.update(tb_seckill_order);
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
	public TbSeckillOrder findOne(Long id){
		return tb_seckill_orderService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			tb_seckill_orderService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	@RequestMapping("submitOrder")
	public Result submitOrder(Long seckillId){
		try {
			String userID = SecurityContextHolder.getContext().getAuthentication().getName();
			if ("anonymousUser".equals(userID)){
				return new Result(false,"用户未登录");
			}
			tb_seckill_orderService.submit(seckillId,userID);
			return new Result(true,"秒杀订单增加成功");
		}catch (RuntimeException r){
			return new Result(false,r.getMessage());
		} catch (Exception e){
			e.printStackTrace();
			return new Result(false,"秒杀订单增加失败");
		}
	}
	
}
