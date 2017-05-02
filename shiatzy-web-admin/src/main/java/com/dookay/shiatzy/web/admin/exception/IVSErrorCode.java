package com.dookay.shiatzy.web.admin.exception;

/**
 * IVS定义的错误代码。
 * @author Luxor
 * @version v0.0.1
 * @since 2016/12/17
 */
public interface IVSErrorCode {

    /*参数格式错误。*/
    Integer INVALID_ARGUMENT = 406;

    /*资源不存在。*/
    Integer NOT_FOUND = 403;

    /*短信发送失败*/
    Integer SMS_SEND_FAILED = 501;
}
