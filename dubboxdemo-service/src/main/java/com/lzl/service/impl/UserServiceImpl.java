package com.lzl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lzl.service.UserService;

/**
 * @Description 本类功能:
 * @Author LZL
 * @Date 2021.04.11-23:10
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String getName() {
        return "Hello World!,我是服务器发来的消息";
    }
}
