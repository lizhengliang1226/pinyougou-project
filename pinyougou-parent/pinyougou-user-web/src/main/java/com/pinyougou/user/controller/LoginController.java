package com.pinyougou.user.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.09-17:46
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/name")
    public Map showUser(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map map=new HashMap();
        map.put("username", name);
        return map;
    }
}
