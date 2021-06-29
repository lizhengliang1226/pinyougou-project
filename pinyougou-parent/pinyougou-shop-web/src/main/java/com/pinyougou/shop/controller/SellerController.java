package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pageentity.Result;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/tb_seller")
public class SellerController {

	@Reference
	private SellerService sellerService;
	@Autowired
	public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	private BCryptPasswordEncoder bCryptPasswordEncoder;
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeller> findAll(){
		return sellerService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return sellerService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param tbSeller
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbSeller tbSeller){
		try {
			tbSeller.setPassword(bCryptPasswordEncoder.encode(tbSeller.getPassword()));
			sellerService.add(tbSeller);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param tbSeller
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbSeller tbSeller){
		try {
			sellerService.update(tbSeller);
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
	public TbSeller findOne(String id){
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		TbSeller one = sellerService.findOne(sellerId);
		return one;
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(String [] ids){
		try {
			sellerService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	@RequestMapping("/confirmPasswd")
	public Result confirm(@RequestBody Map passwd){
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		TbSeller seller = sellerService.findOne(sellerId);
		boolean flag = bCryptPasswordEncoder.matches((CharSequence) passwd.get("old"), seller.getPassword());
		if (flag){
			String aNew = bCryptPasswordEncoder.encode((CharSequence) passwd.get("new"));
			seller.setPassword(aNew);
			sellerService.update(seller);
			return new Result(true,"密码更新成功");
		}else{
			return new Result(false,"旧密码错误，更新失败！");
		}
	}
}
