package com.pinyougou.pageentity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.16-20:15
 */
@Data
@AllArgsConstructor
public class Result implements Serializable {
    private boolean success;
    private String message;
}
