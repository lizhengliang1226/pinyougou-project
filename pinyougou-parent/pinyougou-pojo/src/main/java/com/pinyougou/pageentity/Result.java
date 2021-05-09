package com.pinyougou.pageentity;





import java.io.Serializable;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.16-20:15
 */

public class Result implements Serializable {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result() {
    }

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
