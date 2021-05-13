package com.pinyougou.pageentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.12-21:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {
    private boolean success;
    private String loginName;
    private Object data;
}
