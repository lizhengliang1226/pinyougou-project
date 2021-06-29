package com.pinyougou.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbUserMapper;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 品牌业务逻辑层
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper tb_userMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination smsCodeDestination;
	/**
	 * 增加
	 */
	@Override
	public void add(TbUser tb_user) {
		tb_user.setCreated(new Date());
		tb_user.setUpdated(new Date());
		//密码加密
		String passwd = DigestUtils.md5Hex(tb_user.getPassword());
		tb_user.setPassword(passwd);
		tb_userMapper.insert(tb_user);
	}

	@Override
	public void createSmsCode(String phone) {
		//生成验证码
		long code = RandomUtil.randomLong(100000, 999999);
		System.out.println(code);
		//存入缓存
		redisTemplate.boundValueOps("smsCode_"+phone).set(code+"",5, TimeUnit.MINUTES);
		//发送到消息队列
		Map<String,String> map=new HashMap();
		map.put("phone",phone);
		map.put("smsCode",code+"");
		jmsTemplate.convertAndSend(smsCodeDestination,map);
	}

	@Override
	public boolean checkCode(String code,String phone) {
		String o = (String) redisTemplate.boundValueOps("smsCode_" + phone).get();
		return !StrUtil.isEmpty(o) && code.equals(o);
	}

}
