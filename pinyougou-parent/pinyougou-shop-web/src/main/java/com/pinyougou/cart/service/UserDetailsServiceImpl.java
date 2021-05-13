package com.pinyougou.cart.service;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.20-22:04
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }


    private SellerService sellerService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> list=new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_USER"));
        TbSeller one = sellerService.findOne(username);
        if (ObjectUtils.isEmpty(one)) {
            return null;
        }else{
            if (one.getStatus().equals("1")){
                return new User(username,one.getPassword(),list);
            }else{
                return null;
            }
        }

    }
}
