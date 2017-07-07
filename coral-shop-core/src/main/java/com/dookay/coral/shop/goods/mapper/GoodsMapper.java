package com.dookay.coral.shop.goods.mapper;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品的mapper
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
public interface GoodsMapper extends Mapper<GoodsDomain> {
	
	List<GoodsDomain> getGoodsListByRand(@Param("categoryId") Long categoryId,@Param("limit") int limit);

}
