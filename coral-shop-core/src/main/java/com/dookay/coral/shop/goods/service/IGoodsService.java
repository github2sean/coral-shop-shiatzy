package com.dookay.coral.shop.goods.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.common.web.validate.FieldMatch;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.query.GoodsQuery;

import java.util.List;

/**
 * 商品的业务层接口
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
public interface IGoodsService extends IBaseService<GoodsDomain> {


    List getGoodsList(GoodsQuery query);

}
