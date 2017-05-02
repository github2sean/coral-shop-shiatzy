package com.dookay.coral.shop.goods.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.goods.domain.PrototypeAttributeOptionDomain;

import java.util.List;

/**
 * 原型属性选项的业务层接口
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
public interface IPrototypeAttributeOptionService extends IBaseService<PrototypeAttributeOptionDomain> {

    List getListByAttributeId(Long id);

}
