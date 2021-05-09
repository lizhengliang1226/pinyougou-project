package com.pinyougou.user.controller;
import java.util.List;

import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pageentity.Result;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.user.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import util.PhoneFormatCheckUtils;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/tb_user")
public class UserController {

	@Reference
	private UserService tb_userService;
	

	/**
	 * 增加
	 * @param tb_user
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbUser tb_user,String code){
		if (tb_userService.checkCode(code,tb_user.getPhone())) {
			try {
				tb_userService.add(tb_user);
				return new Result(true, "注册成功");
			} catch (Exception e) {
				e.printStackTrace();
				return new Result(false, "注册失败");
			}
		}else {
			return new Result(false, "验证码错误！");
		}

	}


	/**
	 * 创建验证码
	 * @param phone
	 * @return
	 */
	@RequestMapping("/sendCode")
	public Result createSmsCode(String phone) {
		if (!PhoneFormatCheckUtils.isPhoneLegal(phone)) {
			return new Result(false,"手机号格式不合法");
		}
		try {
			tb_userService.createSmsCode(phone);
			return new Result(true,"发送成功");
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false,"发送失败");
		}
	}
}
