package com.dookay.coral.common.web;

import java.io.Serializable;

/**
 * Json请求返回对象
 * @author Luxor
 * @version v0.0.1
 * @since 2016/11/21
 */
public class JsonResult implements Serializable {
    private int code;
    private String message;
    private Object data;

    public JsonResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
