package com.dookay.shiatzy.web.admin.exception;

import org.springframework.http.HttpStatus;

/**
 * 数据校验异常，返回给用户
 * @author Luxor
 * @version v0.0.1
 * @since 2016/11/29
 */
public class ValidException extends RestException{
    public ValidException(String message) {
        super(HttpStatus.OK,IVSErrorCode.INVALID_ARGUMENT,message);
    }
}
