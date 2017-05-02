package com.dookay.shiatzy.web.admin.exception;

import org.springframework.http.HttpStatus;

/**
 * 请求资源不存在异常
 * @author Luxor
 * @version v0.0.1
 * @since 2016/11/22
 */
public class NotFoundException extends RestException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND,IVSErrorCode.NOT_FOUND,message);
    }
}
