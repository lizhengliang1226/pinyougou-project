package com.pinyougou.manager.controller;
import java.util.List;

import com.pinyougou.content.service.ContentCategoryService;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pageentity.Result;
import com.pinyougou.pojo.TbContentCategory;
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
@RequestMapping("/tb_content_category")
public class ContentCategoryController {

	@Reference
	private ContentCategoryService tb_content_categoryService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbContentCategory> findAll(){
		return tb_content_categoryService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return tb_content_categoryService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param tb_content_category
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbContentCategory tb_content_category){
		try {
			tb_content_categoryService.add(tb_content_category);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param tb_content_category
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbContentCategory tb_content_category){
		try {
			tb_content_categoryService.update(tb_content_category);
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
	public TbContentCategory findOne(Long id){
		return tb_content_categoryService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			tb_content_categoryService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
	
}
