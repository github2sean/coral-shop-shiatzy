package com.dookay.coral.common.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/11/24
 */
public abstract class DomainEvent  extends ApplicationEvent  {
    private CallBack callBack;

    public DomainEvent(Object source) {
        super(source);
    }

    public DomainEvent(Object source,CallBack callBack) {
        super(source);
        this.setCallBack(callBack);
    }

    public CallBack getCallBack() {
        if(this.callBack == null)
            throw new DomainEventException("领域事件回调方法未设置");
        return this.callBack;
    }

    private void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
