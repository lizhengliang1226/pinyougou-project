package com.pinyougou.pageentity;



import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.16-19:19
 */
@Data
@AllArgsConstructor
public class PageResult implements Serializable {
    @Serial
    private static final long serialVersionUID = -4756124157349225444L;
    private long total;
    private List rows;
}
