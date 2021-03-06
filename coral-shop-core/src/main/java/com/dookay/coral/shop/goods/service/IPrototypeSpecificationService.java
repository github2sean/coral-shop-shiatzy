package com.dookay.coral.shop.goods.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.goods.domain.PrototypeSpecificationDomain;

/**
 * 原型规格的业务层接口
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
public interface IPrototypeSpecificationService extends IBaseService<PrototypeSpecificationDomain> {

    PrototypeSpecificationDomain getSpecificationByPrototypeId(Long id);

}
