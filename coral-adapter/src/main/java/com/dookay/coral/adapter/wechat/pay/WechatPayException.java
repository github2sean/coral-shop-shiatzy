package com.dookay.coral.adapter.wechat.pay;

import com.dookay.coral.common.exception.BaseException;
import com.dookay.coral.common.exception.ServiceException;
import org.omg.CORBA.portable.ApplicationException;

/**
 * Created by 磊 on 2017/3/22.
 */
public class WechatPayException extends Exception{
    public WechatPayException(String message) {
        super(message);
    }
}
