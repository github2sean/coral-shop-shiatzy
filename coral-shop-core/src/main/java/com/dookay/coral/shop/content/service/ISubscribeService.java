package com.dookay.coral.shop.content.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.content.domain.SubscribeDomain;

/**
 * 订阅的业务层接口
 * @author : luxor
 * @since : 2017年06月14日
 * @version : v0.0.1
 */
public interface ISubscribeService extends IBaseService<SubscribeDomain> {
    void createSubscribe(String email);
}
