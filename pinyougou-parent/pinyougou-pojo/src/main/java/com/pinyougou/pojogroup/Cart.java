package com.pinyougou.pojogroup;

import com.pinyougou.pojo.TbOrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.10-20:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart implements Serializable {

    private static final long serialVersionUID = -4845564704299074702L;
    private String sellerId;
    private String sellerName;
    private List<TbOrderItem> orderItemList;
}
