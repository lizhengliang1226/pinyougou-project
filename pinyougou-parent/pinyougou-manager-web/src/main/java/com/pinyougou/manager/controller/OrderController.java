package com.pinyougou.manager.controller;

import com.pinyougou.sellergoods.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("tb_order")
public class OrderController {

	@Reference
	private OrderService tb_orderService;

	@RequestMapping("orderNum")
	public int orderNum(){
		return tb_orderService.orderNum();
	}
}
