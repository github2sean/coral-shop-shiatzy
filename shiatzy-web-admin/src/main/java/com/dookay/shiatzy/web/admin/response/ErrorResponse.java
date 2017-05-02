package com.dookay.shiatzy.web.admin.response;

/**
 * 返回结果基类
 * @author Luxor
 * @version 2016/11/2.
 */
public class ErrorResponse {

    private Integer code;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
