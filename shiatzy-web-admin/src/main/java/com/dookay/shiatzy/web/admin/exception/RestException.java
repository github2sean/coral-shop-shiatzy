package com.dookay.shiatzy.web.admin.exception;

import org.springframework.http.HttpStatus;

/**
 * api通用异常
 * @author Luxor
 * @version 2016/11/2.
 */
public class RestException extends RuntimeException {

    public HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    private Integer code;

    public RestException() {
    }

    public RestException(HttpStatus status) {
        this.status = status;
    }

    public RestException(String message) {
        super(message);
    }

    public RestException(Integer code,String message){
        super(message);
        this.code = code;
    }

    public RestException(HttpStatus status,Integer code,String message){
        super(message);
        this.status = status;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}