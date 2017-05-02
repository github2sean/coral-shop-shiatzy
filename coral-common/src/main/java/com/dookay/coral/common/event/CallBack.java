package com.dookay.coral.common.event;

/**
 * 事件回调
 * @author Luxor
 * @version v0.0.1
 * @since 2016/11/24
 */
public interface CallBack {
    void onSuccess(Object object);
    void OnError(Exception e);
}
