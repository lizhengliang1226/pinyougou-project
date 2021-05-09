package com.pinyougou.pageentity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.16-19:19
 */

public class PageResult implements Serializable {
    public PageResult(long total, List rows) {
        this.total = total;
        this.rows = rows;
    }



    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public PageResult() {
    }

    @Serial
    private static final long serialVersionUID = -4756124157349225444L;
    private long total;
    private List rows;
}
