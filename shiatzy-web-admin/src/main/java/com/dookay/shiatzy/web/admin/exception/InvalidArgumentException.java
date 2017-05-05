package com.dookay.shiatzy.web.admin.exception;

import org.springframework.http.HttpStatus;

/**
 * 参数错误
 * @author Luxor
 * @version v0.0.1
 * @since 2016/12/17
 */
public class InvalidArgumentException extends RestException {
    public InvalidArgumentException(String message) {
        super(HttpStatus.OK,IVSErrorCode.INVALID_ARGUMENT,message);
    }
}
