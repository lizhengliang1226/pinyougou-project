package com.pinyougou.user.service;
import com.pinyougou.pageentity.PageResult;
import com.pinyougou.pojo.TbUser;

import java.util.List;

/**
 * 品牌服务层接口
 * @author Administrator
 *
 */
public interface UserService {

	/**
	 * 注册
	*/
	public void add(TbUser tb_user);


	/**
	 * 创建短信验证码
	 * @param phone
	 */
	public void createSmsCode(String phone);

	/**
	 * 检查验证码
	 * @param code
	 * @return
	 */
	public boolean checkCode(String code,String phone);
}
