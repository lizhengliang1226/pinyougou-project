package com.lzl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lzl.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 本类功能:
 * @Author LZL
 * @Date 2021.04.11-23:22
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("/show-name")
    @ResponseBody
    public String showName(){
        System.out.println(userService.getName());
        return userService.getName();
    }
}
